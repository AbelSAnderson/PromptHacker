package objects

import objects.commands.*
import objects.files.*

import org.apache.commons.io.FileUtils
import org.json.JSONArray
import org.json.JSONObject

import java.io.BufferedReader
import java.io.File as ZFile
import java.io.IOException
import java.io.InputStreamReader
import java.net.URL

class GameState {
    val commands: List<Command> = listOf(Help(), Get(), Set(), Ping(), Connect(), Login(), Scan(), Ls(), Cat(), Cd(), Rm(), Mv(), Exit())
    val ipAddresses: MutableList<String> = mutableListOf()
    val error = Error()

    var isExit: Boolean = false
    var activeComputers: ArrayList<Computer> = ArrayList()
    var currentComputer: Computer

    init {
        //Get local IP Address
        val ipAddress: String
        ipAddress = try {
            val tempIpAddress = BufferedReader(InputStreamReader(URL("http://bot.whatismyipaddress.com").openStream())).readLine().trim()
            ipAddresses.add(tempIpAddress)
            tempIpAddress
        } catch (e: Exception) {
            Ping().generateIP(this)
        }

        currentComputer = createComputer(ZFile("src/resources/Root.json"), ipAddress)
        currentComputer.security.isLocked = false
    }

    //Methods

    //Create a new Computer based off of a Json File
    fun createComputer(jsonFile: ZFile, ipAddress: String): Computer {
        val content: String = try {
            FileUtils.readFileToString(jsonFile, "utf-8")
        } catch (e: IOException) {
            e.printStackTrace().toString()
        }

        val computer = JSONObject(content)

        //Grab information from Json File
        val compName = computer.getString("Name")
        val security = createSecurity(computer.getJSONObject("Security"))

        //Grab and convert JSON arrays

        //Connected Computers
        val connectedComps = computer.getJSONArray("ConnectedComputers")
        val connectedComputersFiles = mutableListOf<String>()

        for (i in 0 until connectedComps.length()) {
            connectedComputersFiles.add(connectedComps.getString(i))
        }

        //Files
        val files = createFolder(computer.getJSONObject("Files"))
        files.setParents(null)

        //Emails
        val emails = createEmails(computer.getJSONArray("Emails"))

        val newComputer = Computer(compName, security, ipAddress, connectedComputersFiles, mutableListOf(), files, files, emails)
        activeComputers.add(newComputer)

        //Create & Return Completed Computer
        return newComputer
    }

    private fun createEmails(emails: JSONArray): Array<Email> {
        val emailList = mutableListOf<Email>()

        for (email in emails) {
            emailList.add(createEmail(email as JSONObject))
        }

        return emailList.toTypedArray()
    }

    private fun createEmail(email: JSONObject): Email {
        val fileName = email.getString("Name")
        val from = email.getString("From")
        val to = email.getString("To")
        val sent = email.getString("Sent")
        val subject = email.getString("Subject")
        val message = email.getString("Message")

        return Email(fileName, from, to, sent, subject, message)
    }

    private fun createFile(file: JSONObject): File {
        val fileName = file.getString("Name")
        val content = file.getString("Content")

        return TextFile(fileName, content)
    }

    private fun createFolder(folder: JSONObject): Folder {
        val folderName = folder.getString("Name")

        val folderContentObjects = folder.getJSONArray("Content")
        val tempFolder: ArrayList<File> = ArrayList(folderContentObjects.length())

        for (i in 0 until folderContentObjects.length()) {
            val file = folderContentObjects.getJSONObject(i)

            when (file.getString("Type")) {
                "text" -> tempFolder.add(createFile(file))
                "folder" -> tempFolder.add(createFolder(file))
            }
        }

        return Folder(folderName, tempFolder)
    }

    private fun createSecurity(info: JSONObject): SecuritySys {
        val ports = createPorts(info.getJSONArray("Ports"))
        val password = info.getString("Password")
        val hint = info.getString("Hint")

        return SecuritySys(ports, password, hint)
    }

    private fun createPorts(portList: JSONArray): Array<Port> {
        val masterPortList: HashMap<Int, String> = hashMapOf(22 to "SSH", 80 to "HTTP")
        val finishedList: MutableList<Port> = mutableListOf()

        for (port in portList) {
            val temp = port.toString().toInt()
            finishedList.add(Port(masterPortList.getValue(temp), temp))
        }

        return finishedList.toTypedArray()
    }
}