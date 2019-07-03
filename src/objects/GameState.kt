package objects

import objects.commands.*
import objects.files.*

import objects.File as ZFile

import org.apache.commons.io.FileUtils
import org.json.JSONObject

import java.io.BufferedReader
import java.io.File
import java.io.IOException
import java.io.InputStreamReader
import java.net.URL

class GameState {

    var isExit: Boolean = false
    var commands: Array<Command>
    var currentComputer: Computer
    var connectedIPs: Array<String>

    init {
        isExit = false
        commands = arrayOf(Help(), Scan(), Ls(), Cat(), Cd(), Exit())
        connectedIPs = arrayOf()

        //Get local IP Address
        val ipAddress: String = try {
            BufferedReader(InputStreamReader(URL("http://bot.whatismyipaddress.com").openStream())).readLine().trim { it <= ' ' }
        } catch (e: Exception) {
            Scan().generateIP()
        }

        currentComputer = createComputer(File("src/resources/startComp.json"), ipAddress)
    }

    //Methods
    private fun createComputer(jsonFile: File, ipAddress: String): Computer {

        var content: String

        try {
            content = FileUtils.readFileToString(jsonFile, "utf-8")
        } catch (e: IOException) {
            content = ""
            e.printStackTrace()
        }

        val computer = JSONObject(content)

        //Grab information from Json File
        val compName = computer.getString("Name")
        val security = computer.getString("Security")

        //Grab and convert JSON arrays

        //Connected Computers
        val connectedComps = computer.getJSONArray("ConnectedComputers")
        val connectedComputers = arrayOfNulls<String>(connectedComps.length())

        for (i in 0 until connectedComps.length()) {
            connectedComputers[i] = connectedComps.getString(i)
        }

        //files
        val folder = computer.getJSONObject("Files")

        val files = createFolder(folder)
        files.setParents(null)

        //Return Completed Computer
        return Computer(compName, security, ipAddress, connectedComputers, files)
    }

    private fun createEmail(email: JSONObject): ZFile {
        val fileName = email.getString("Name")
        val from = email.getString("From")
        val to = email.getString("To")
        val sent = email.getString("Sent")
        val subject = email.getString("Subject")
        val message = email.getString("Message")

        return Email(fileName, from, to, sent, subject, message)
    }

    private fun createFile(file: JSONObject): ZFile {
        val fileName = file.getString("Name")
        val content = file.getString("Content")

        return TextFile(fileName, content)
    }

    private fun createFolder(folder: JSONObject): Folder {
        val folderName = folder.getString("Name")

        val folderContentObjects = folder.getJSONArray("Content")
        val tempFolder: ArrayList<ZFile> = ArrayList(folderContentObjects.length())

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
}