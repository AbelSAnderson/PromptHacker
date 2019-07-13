package objects

import objects.commands.*
import objects.files.*

import org.apache.commons.io.FileUtils
import org.json.JSONObject

import java.io.BufferedReader
import java.io.File as ZFile
import java.io.IOException
import java.io.InputStreamReader
import java.net.URL

class GameState {

    val commands: List<Command> = listOf(Help(), View(), Set(), Scan(), Connect(), Login(), Ls(), Cat(), Cd(), Rm(), Exit())
    val error = Error()

    var isExit: Boolean = false
    var activeComputers: ArrayList<Computer> = ArrayList()
    var currentComputer: Computer

    init {

        //Get local IP Address
        val ipAddress: String = try {
            BufferedReader(InputStreamReader(URL("http://bot.whatismyipaddress.com").openStream())).readLine().trim { it <= ' ' }
        } catch (e: Exception) {
            Scan().generateIP()
        }

        currentComputer = createComputer(ZFile("src/resources/PortHackExe.json"), ipAddress)
        activeComputers.add(currentComputer)
    }

    //Methods
    fun createComputer(jsonFile: ZFile, ipAddress: String): Computer {

        val content: String = try {
            FileUtils.readFileToString(jsonFile, "utf-8")
        } catch (e: IOException) {
            ""
        }

        val computer = JSONObject(content)

        //Grab information from Json File
        val compName = computer.getString("Name")
        val security = createSecurity(computer.getJSONObject("Security"))

        //Grab and convert JSON arrays

        //Connected Computers
        val connectedComps = computer.getJSONArray("ConnectedComputers")
        val connectedComputersFiles = arrayOfNulls<String>(connectedComps.length())

        for (i in 0 until connectedComps.length()) {
            connectedComputersFiles[i] = connectedComps.getString(i)
        }

        //Files
        val files = createFolder(computer.getJSONObject("Files"))
        files.setParents(null)

        //Create & Return Completed Computer
        return Computer(compName, security, ipAddress, connectedComputersFiles, files)
    }

    private fun createEmail(email: JSONObject): File {
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
                "email" -> tempFolder.add(createEmail(file))
                "folder" -> tempFolder.add(createFolder(file))
            }
        }

        return Folder(folderName, tempFolder)
    }

    private fun createSecurity(info: JSONObject): SecuritySys {
        val password = info.getString("Password")
        val hint = info.getString("Hint")
        val unlocked = password.isNotEmpty()

        return SecuritySys(password, hint, unlocked)
    }
}