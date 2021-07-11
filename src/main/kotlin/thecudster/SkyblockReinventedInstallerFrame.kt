import java.awt.*
import java.awt.event.ActionEvent
import java.awt.event.ActionListener
import java.awt.event.MouseEvent
import java.awt.event.MouseListener
import java.io.*
import java.net.URI
import java.net.URISyntaxException
import java.nio.file.Files
import java.util.*
import java.util.jar.JarFile
import java.util.regex.Pattern
import javax.imageio.ImageIO
import javax.swing.*

/**
 * Taken from SkyblockAddons under MIT License
 * https://github.com/BiscuitDevelopment/SkyblockAddons/blob/master/LICENSE
 * @author BiscuitDevelopment
 */
class SkyblockReinventedInstallerFrame() : JFrame(), ActionListener, MouseListener {
    private var logo: JLabel? = null
    private var versionInfo: JLabel? = null
    private var labelFolder: JLabel? = null
    private var panelCenter: JPanel? = null
    private var panelBottom: JPanel? = null
    private var totalContentPane: JPanel? = null
    private var descriptionText: JTextArea? = null
    private var forgeDescriptionText: JTextArea? = null
    private var textFieldFolderLocation: JTextField? = null
    private var buttonChooseFolder: JButton? = null
    private var buttonInstall: JButton? = null
    private var buttonOpenFolder: JButton? = null
    private var buttonClose: JButton? = null
    private var frameX = 0
    private var frameY = 0
    private var w = TOTAL_WIDTH
    private var h = 0
    private var margin = 0
    private val panelContentPane: JPanel?
        get() {
            if (totalContentPane == null) {
                try {
                    totalContentPane = JPanel()
                    totalContentPane!!.name = "PanelContentPane"
                    totalContentPane!!.layout = BorderLayout(5, 5)
                    totalContentPane!!.preferredSize = Dimension(TOTAL_WIDTH, TOTAL_HEIGHT)
                    totalContentPane!!.add(getPanelCenter(), "Center")
                    totalContentPane!!.add(getPanelBottom(), "South")
                } catch (ivjExc: Throwable) {
                    showErrorPopup(ivjExc)
                }
            }
            return totalContentPane
        }

    private fun getPanelCenter(): JPanel? {
        if (panelCenter == null) {
            try {
                (JPanel().also { panelCenter = it }).name = "PanelCenter"
                panelCenter!!.layout = null
                panelCenter!!.add(pictureLabel, pictureLabel!!.name)
                panelCenter!!.add(getVersionInfo(), getVersionInfo()!!.name)
                panelCenter!!.add(textArea, textArea!!.name)
                panelCenter!!.add(forgeTextArea, forgeTextArea!!.name)
                panelCenter!!.add(getLabelFolder(), getLabelFolder()!!.name)
                panelCenter!!.add(fieldFolder, fieldFolder!!.name)
                panelCenter!!.add(buttonFolder, buttonFolder!!.name)
            } catch (ivjExc: Throwable) {
                showErrorPopup(ivjExc)
            }
        }
        return panelCenter
    }

    private val pictureLabel: JLabel?
            get() {
            if (logo == null) {
                try {
                    h = w / 2
                    margin = 5
                    val myPicture = ImageIO.read(
                        Objects.requireNonNull(
                            javaClass.classLoader
                                .getResourceAsStream("assets/sre/gui/logo.png"), "Logo not found."
                        )
                    )
                    val scaled = myPicture.getScaledInstance(w / 2 - margin * 2, h - margin, Image.SCALE_SMOOTH)
                    logo = JLabel(ImageIcon(scaled))
                    logo!!.name = "Logo"
                    logo!!.setBounds(frameX + margin, frameY + margin, w - margin * 2, h - margin)
                    logo!!.font = Font(Font.DIALOG, Font.BOLD, 18)
                    logo!!.horizontalAlignment = SwingConstants.CENTER
                    logo!!.preferredSize = Dimension(w, h)
                    frameY += h
                } catch (ivjExc: Throwable) {
                    showErrorPopup(ivjExc)
                }
            }
            return logo
        }

    private fun getVersionInfo(): JLabel? {
        if (versionInfo == null) {
            try {
                h = 25
                versionInfo = JLabel()
                versionInfo!!.name = "LabelMcVersion"
                versionInfo!!.setBounds(frameX, frameY, w, h)
                versionInfo!!.font = Font(Font.DIALOG, Font.BOLD, 14)
                versionInfo!!.horizontalAlignment = SwingConstants.CENTER
                versionInfo!!.preferredSize = Dimension(w, h)
                versionInfo!!.text = "v$versionFromMcmodInfo by the SRE Team - for Minecraft 1.8.9"
                frameY += h
            } catch (ivjExc: Throwable) {
                showErrorPopup(ivjExc)
            }
        }
        return versionInfo
    }

    private val textArea: JTextArea?
        get() {
            if (descriptionText == null) {
                try {
                    h = 60
                    margin = 10
                    descriptionText = JTextArea()
                    descriptionText!!.name = "TextArea"
                    setTextAreaProperties(descriptionText!!)
                    descriptionText!!.text =
                        "This installer will copy SkyblockReinvented into your forge mods folder for you, and replace any old versions that already exist. " +
                                "Close this if you prefer to do this yourself!"
                    descriptionText!!.wrapStyleWord = true
                    frameY += h
                } catch (ivjExc: Throwable) {
                    showErrorPopup(ivjExc)
                }
            }
            return descriptionText
        }

    private fun setTextAreaProperties(textArea: JTextArea) {
        textArea.setBounds(frameX + margin, frameY + margin, w - margin * 2, h - margin)
        textArea.isEditable = false
        textArea.highlighter = null
        textArea.isEnabled = true
        textArea.font = Font(Font.DIALOG, Font.PLAIN, 12)
        textArea.lineWrap = true
        textArea.isOpaque = false
        textArea.preferredSize = Dimension(w - margin * 2, h - margin)
    }

    private val forgeTextArea: JTextArea?
        get() {
            if (forgeDescriptionText == null) {
                try {
                    h = 55
                    margin = 10
                    forgeDescriptionText = JTextArea()
                    forgeDescriptionText!!.name = "TextAreaForge"
                    setTextAreaProperties(forgeDescriptionText!!)
                    forgeDescriptionText!!.text =
                        "However, you still need to install Forge client in order to be able to run this mod. Click here to visit the download page for Forge 1.8.9!"
                    forgeDescriptionText!!.foreground = Color.BLUE.darker()
                    forgeDescriptionText!!.cursor = Cursor.getPredefinedCursor(Cursor.HAND_CURSOR)
                    forgeDescriptionText!!.wrapStyleWord = true
                    frameY += h
                } catch (ivjExc: Throwable) {
                    showErrorPopup(ivjExc)
                }
            }
            return forgeDescriptionText
        }

    private fun getLabelFolder(): JLabel? {
        if (labelFolder == null) {
            h = 16
            w = 65
            frameX += 10 // Padding
            try {
                labelFolder = JLabel()
                labelFolder!!.name = "LabelFolder"
                labelFolder!!.setBounds(frameX, frameY + 2, w, h)
                labelFolder!!.preferredSize = Dimension(w, h)
                labelFolder!!.text = "Mods Folder"
            } catch (ivjExc: Throwable) {
                showErrorPopup(ivjExc)
            }
            frameX += w
        }
        return labelFolder
    }

    private val fieldFolder: JTextField?
        get() {
            if (textFieldFolderLocation == null) {
                h = 20
                w = 287
                try {
                    textFieldFolderLocation = JTextField()
                    textFieldFolderLocation!!.name = "FieldFolder"
                    textFieldFolderLocation!!.setBounds(frameX, frameY, w, h)
                    textFieldFolderLocation!!.isEditable = false
                    textFieldFolderLocation!!.preferredSize = Dimension(w, h)
                } catch (ivjExc: Throwable) {
                    showErrorPopup(ivjExc)
                }
                frameX += w
            }
            return textFieldFolderLocation
        }

    // Padding
    private val buttonFolder: JButton?
        get() {
            if (buttonChooseFolder == null) {
                h = 20
                w = 25
                frameX += 10 // Padding
                try {
                    val myPicture = ImageIO.read(
                        Objects.requireNonNull(
                            javaClass.classLoader
                                .getResourceAsStream("assets/sre/gui/folder.png"), "Folder icon not found."
                        )
                    )
                    val scaled = myPicture.getScaledInstance(w - 8, h - 6, Image.SCALE_SMOOTH)
                    buttonChooseFolder = JButton(ImageIcon(scaled))
                    buttonChooseFolder!!.name = "ButtonFolder"
                    buttonChooseFolder!!.setBounds(frameX, frameY, w, h)
                    buttonChooseFolder!!.preferredSize = Dimension(w, h)
                } catch (ivjExc: Throwable) {
                    showErrorPopup(ivjExc)
                }
            }
            return buttonChooseFolder
        }

    private fun getPanelBottom(): JPanel? {
        if (panelBottom == null) {
            try {
                panelBottom = JPanel()
                panelBottom!!.name = "PanelBottom"
                panelBottom!!.layout = FlowLayout(FlowLayout.CENTER, 15, 10)
                panelBottom!!.preferredSize = Dimension(390, 55)
                panelBottom!!.add(getButtonInstall(), getButtonInstall()!!.name)
                panelBottom!!.add(getButtonOpenFolder(), getButtonOpenFolder()!!.name)
                panelBottom!!.add(getButtonClose(), getButtonClose()!!.name)
            } catch (ivjExc: Throwable) {
                showErrorPopup(ivjExc)
            }
        }
        return panelBottom
    }

    private fun getButtonInstall(): JButton? {
        if (buttonInstall == null) {
            w = 100
            h = 26
            try {
                buttonInstall = JButton()
                buttonInstall!!.name = "ButtonInstall"
                buttonInstall!!.preferredSize = Dimension(w, h)
                buttonInstall!!.text = "Install"
            } catch (ivjExc: Throwable) {
                showErrorPopup(ivjExc)
            }
        }
        return buttonInstall
    }

    private fun getButtonOpenFolder(): JButton? {
        if (buttonOpenFolder == null) {
            w = 130
            h = 26
            try {
                buttonOpenFolder = JButton()
                buttonOpenFolder!!.name = "ButtonOpenFolder"
                buttonOpenFolder!!.preferredSize = Dimension(w, h)
                buttonOpenFolder!!.text = "Open Mods Folder"
            } catch (ivjExc: Throwable) {
                showErrorPopup(ivjExc)
            }
        }
        return buttonOpenFolder
    }

    private fun getButtonClose(): JButton? {
        if (buttonClose == null) {
            w = 100
            h = 26
            try {
                (JButton().also { buttonClose = it }).name = "ButtonClose"
                buttonClose!!.preferredSize = Dimension(w, h)
                buttonClose!!.text = "Cancel"
            } catch (ivjExc: Throwable) {
                showErrorPopup(ivjExc)
            }
        }
        return buttonClose
    }

    fun onFolderSelect() {
        val currentDirectory = File(fieldFolder!!.text)
        val jFileChooser = JFileChooser(currentDirectory)
        jFileChooser.fileSelectionMode = JFileChooser.DIRECTORIES_ONLY
        jFileChooser.isAcceptAllFileFilterUsed = false
        if (jFileChooser.showOpenDialog(this) == 0) {
            val newDirectory = jFileChooser.selectedFile
            fieldFolder!!.text = newDirectory.path
        }
    }

    override fun actionPerformed(e: ActionEvent) {
        if (e.source === getButtonClose()) {
            dispose()
            System.exit(0)
        }
        if (e.source === buttonFolder) {
            onFolderSelect()
        }
        if (e.source === getButtonInstall()) {
            onInstall()
        }
        if (e.source === getButtonOpenFolder()) {
            onOpenFolder()
        }
    }

    override fun mouseClicked(e: MouseEvent) {
        if (e.source === forgeTextArea) {
            try {
                Desktop.getDesktop()
                    .browse(URI("http://files.minecraftforge.net/maven/net/minecraftforge/forge/index_1.8.9.html"))
            } catch (ex: IOException) {
                showErrorPopup(ex)
            } catch (ex: URISyntaxException) {
                showErrorPopup(ex)
            }
        }
    }

    fun onInstall() {
        try {
            val modsFolder = File(fieldFolder!!.text)
            if (!modsFolder.exists()) {
                showErrorMessage("Folder not found: " + modsFolder.path)
                return
            }
            if (!modsFolder.isDirectory) {
                showErrorMessage("Not a folder: " + modsFolder.path)
                return
            }
            tryInstall(modsFolder)
        } catch (e: Exception) {
            showErrorPopup(e)
        }
    }

    private fun tryInstall(modsFolder: File) {
        val thisFile = thisFile
        if (thisFile != null) {
            val inSubFolder = IN_MODS_SUBFOLDER.matcher(modsFolder.path).find()
            val newFile = File(modsFolder, "sre-$versionFromMcmodInfo.jar")
            if (thisFile == newFile) {
                showErrorMessage("You are opening this file from where the file should be installed... there's nothing to be done!")
                return
            }
            var deletingFailure = false
            if (modsFolder.isDirectory) { // Delete in this current folder.
                val failed = findSkyblockReinventedAndDelete(modsFolder.listFiles())
                if (failed) deletingFailure = true
            }
            if (inSubFolder) { // We are in the 1.8.9 folder, delete in the parent folder as well.
                if (modsFolder.parentFile.isDirectory) {
                    val failed = findSkyblockReinventedAndDelete(modsFolder.parentFile.listFiles())
                    if (failed) deletingFailure = true
                }
            } else { // We are in the main mods folder, but the 1.8.9 subfolder exists... delete in there too.
                val subFolder = File(modsFolder, "1.8.9")
                if (subFolder.exists() && subFolder.isDirectory) {
                    val failed = findSkyblockReinventedAndDelete(subFolder.listFiles())
                    if (failed) deletingFailure = true
                }
            }
            if (deletingFailure) return
            if (thisFile.isDirectory) {
                showErrorMessage("This file is a directory... Are we in a development environment?")
                return
            }
            try {
                Files.copy(thisFile.toPath(), newFile.toPath())
            } catch (ex: Exception) {
                showErrorPopup(ex)
                return
            }
            showMessage("SkyblockReinvented has been successfully installed into your mods folder.")
            dispose()
            System.exit(0)
        }
    }

    private fun findSkyblockReinventedAndDelete(files: Array<File>?): Boolean {
        if (files == null) return false
        for (file: File in files) {
            if (!file.isDirectory && file.path.endsWith(".jar")) {
                try {
                    val jarFile = JarFile(file)
                    val mcModInfo = jarFile.getEntry("mcmod.info")
                    if (mcModInfo != null) {
                        val inputStream = jarFile.getInputStream(mcModInfo)
                        val modID = getModIDFromInputStream(inputStream)
                        if (modID == "sre") {
                            jarFile.close()
                            try {
                                val deleted = file.delete()
                                if (!deleted) {
                                    throw Exception()
                                }
                            } catch (ex: Exception) {
                                ex.printStackTrace()
                                showErrorMessage(
                                    "Was not able to delete the other SkyblockReinvented files found in your mods folder!" + System.lineSeparator() +
                                            "Please make sure that your minecraft is currently closed and try again, or feel" + System.lineSeparator() +
                                            "free to open your mods folder and delete those files manually."
                                )
                                return true
                            }
                            continue
                        }
                    }
                    jarFile.close()
                } catch (ex: Exception) {
                    // Just don't check the file I guess, move on to the next...
                }
            }
        }
        return false
    }

    fun onOpenFolder() {
        try {
            Desktop.getDesktop().open(modsFolder)
        } catch (e: Exception) {
            showErrorPopup(e)
        }
    }

    val modsFolder: File
        get() {
            val userHome = System.getProperty("user.home", ".")
            var modsFolder = getFile(userHome, "minecraft/mods/1.8.9")
            if (!modsFolder.exists()) {
                modsFolder = getFile(userHome, "minecraft/mods")
            }
            if (!modsFolder.exists() && !modsFolder.mkdirs()) {
                throw RuntimeException("The working directory could not be created: $modsFolder")
            }
            return modsFolder
        }

    fun getFile(userHome: String?, minecraftPath: String): File {
        var workingDirectory: File?
        when (operatingSystem) {
            OperatingSystem.LINUX, OperatingSystem.SOLARIS -> {
                workingDirectory = File(userHome, ".$minecraftPath/")
            }
            OperatingSystem.WINDOWS -> {
                val applicationData = System.getenv("APPDATA")
                if (applicationData != null) {
                    workingDirectory = File(applicationData, ".$minecraftPath/")
                } else {
                    workingDirectory = File(userHome, ".$minecraftPath/")
                }
            }
            OperatingSystem.MACOS -> {
                workingDirectory = File(userHome, "Library/Application Support/$minecraftPath")
            }
            else -> {
                workingDirectory = File(userHome, "$minecraftPath/")
            }
        }
        return workingDirectory
    }

    val operatingSystem: OperatingSystem
        get() {
            val osName = System.getProperty("os.name").lowercase()
            if (osName.contains("win")) {
                return OperatingSystem.WINDOWS
            } else if (osName.contains("mac")) {
                return OperatingSystem.MACOS
            } else if (osName.contains("solaris") || osName.contains("sunos")) {
                return OperatingSystem.SOLARIS
            } else if (osName.contains("linux") || osName.contains("unix")) {
                return OperatingSystem.LINUX
            }
            return OperatingSystem.UNKNOWN
        }

    fun centerFrame(frame: JFrame) {
        val rectangle = frame.bounds
        val screenSize = Toolkit.getDefaultToolkit().screenSize
        val screenRectangle = Rectangle(0, 0, screenSize.width, screenSize.height)
        var newX = screenRectangle.x + (screenRectangle.width - rectangle.width) / 2
        var newY = screenRectangle.y + (screenRectangle.height - rectangle.height) / 2
        if (newX < 0) newX = 0
        if (newY < 0) newY = 0
        frame.setBounds(newX, newY, rectangle.width, rectangle.height)
    }

    fun showMessage(message: String?) {
        JOptionPane.showMessageDialog(null, message, "SkyblockReinvented Installer", JOptionPane.INFORMATION_MESSAGE)
    }

    fun showErrorMessage(message: String?) {
        JOptionPane.showMessageDialog(null, message, "SkyblockReinvented - Error", JOptionPane.ERROR_MESSAGE)
    }

    enum class OperatingSystem {
        LINUX, SOLARIS, WINDOWS, MACOS, UNKNOWN
    }

    // It's okay, I guess just don't use the version lol.
    private val versionFromMcmodInfo: String
        get() {
            var version = ""
            try {
                val bufferedReader = BufferedReader(
                    InputStreamReader(
                        Objects.requireNonNull(
                            javaClass.classLoader.getResourceAsStream("mcmod.info"),
                            "mcmod.info not found."
                        )
                    )
                )
                while ((bufferedReader.readLine().also { version = it }) != null) {
                    if (version.contains("\"version\": \"")) {
                        version = version.split(Pattern.quote("\"version\": \"")).toTypedArray()[1]
                        version = version.substring(0, version.length - 2)
                        break
                    }
                }
            } catch (ex: Exception) {
                // It's okay, I guess just don't use the version lol.
            }
            return version
        }

    private fun getModIDFromInputStream(inputStream: InputStream): String {
        var version = ""
        try {
            val bufferedReader = BufferedReader(InputStreamReader(inputStream))
            while ((bufferedReader.readLine().also { version = it }) != null) {
                if (version.contains("\"modid\": \"")) {
                    version = version.split("\"modid\": \"").toTypedArray()[1]
                    version = version.substring(0, version.length - 2)
                    break
                }
            }
        } catch (ex: Exception) {
            // RIP, couldn't find the modid...
        }
        return version
    }

    private val thisFile: File?
        get() {
            try {
                return File(SkyblockReinventedInstallerFrame::class.java.protectionDomain.codeSource.location.toURI())
            } catch (ex: URISyntaxException) {
                showErrorPopup(ex)
            }
            return null
        }

    override fun mousePressed(e: MouseEvent) {}
    override fun mouseReleased(e: MouseEvent) {}
    override fun mouseEntered(e: MouseEvent) {}
    override fun mouseExited(e: MouseEvent) {}

    companion object {
        private val IN_MODS_SUBFOLDER = Pattern.compile("1\\.8\\.9[/\\\\]?$")
        private val TOTAL_HEIGHT = 435
        private val TOTAL_WIDTH = 404
        @JvmStatic
        fun main(args: Array<String>) {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName())
                val frame = SkyblockReinventedInstallerFrame()
                frame.centerFrame(frame)
                frame.isVisible = true
            } catch (ex: Exception) {
                showErrorPopup(ex)
            }
        }

        private fun getStacktraceText(ex: Throwable): String {
            val stringWriter = StringWriter()
            ex.printStackTrace(PrintWriter(stringWriter))
            return stringWriter.toString().replace("\t", "  ")
        }

        private fun showErrorPopup(ex: Throwable) {
            ex.printStackTrace()
            val textArea = JTextArea(getStacktraceText(ex))
            textArea.isEditable = false
            val currentFont = textArea.font
            val newFont = Font(Font.MONOSPACED, currentFont.style, currentFont.size)
            textArea.font = newFont
            val errorScrollPane = JScrollPane(textArea)
            errorScrollPane.preferredSize = Dimension(600, 400)
            JOptionPane.showMessageDialog(null, errorScrollPane, "Error", JOptionPane.ERROR_MESSAGE)
        }
    }

    init {
        try {
            name = "SkyblockReinventedInstallerFrame"
            title = "SkyblockReinvented Installer"
            isResizable = false
            setSize(TOTAL_WIDTH, TOTAL_HEIGHT)
            contentPane = panelContentPane
            buttonFolder!!.addActionListener(this)
            getButtonInstall()!!.addActionListener(this)
            getButtonOpenFolder()!!.addActionListener(this)
            getButtonClose()!!.addActionListener(this)
            forgeTextArea!!.addMouseListener(this)
            pack()
            defaultCloseOperation = WindowConstants.EXIT_ON_CLOSE
            fieldFolder!!.text = modsFolder.path
            getButtonInstall()!!.isEnabled = true
            getButtonInstall()!!.requestFocus()
        } catch (ex: Exception) {
            showErrorPopup(ex)
        }
    }
}