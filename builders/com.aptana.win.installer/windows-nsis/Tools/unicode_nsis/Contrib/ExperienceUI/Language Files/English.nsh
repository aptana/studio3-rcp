;NSIS ExperienceUI - Language File
;Compatible with ExperienceUI 1.09�

;Language: English (1033)
;By dandaman32

;--------------------------------

!insertmacro XPUI_LANGUAGEFILE_BEGIN "English"

; Use only ASCII characters (if this is not possible, use the English name)
!insertmacro XPUI_UNSET XPUI_LANGNAME
!insertmacro XPUI_DEFAULT XPUI_LANGNAME "English"

; BUTTONS
!insertmacro XPUI_DEFAULT XPUI_BUTTONTEXT_NEXT   Next
!insertmacro XPUI_DEFAULT XPUI_BUTTONTEXT_BACK   Back
!insertmacro XPUI_DEFAULT XPUI_BUTTONTEXT_CANCEL Cancel
!insertmacro XPUI_DEFAULT XPUI_BUTTONTEXT_CLOSE  Close

!insertmacro XPUI_DEFAULT XPUI_ABORTWARNING_TEXT "You are about to quit setup.$\n$\nIf you quit now, $(^Name) will not be installed.$\n$\nDo you want to continue?"

; +---------+
; | INSTALL |
; +---------+

; WELCOME PAGE
!insertmacro XPUI_DEFAULT XPUI_WELCOMEPAGE_TEXT_TOP "Welcome to the $(^Name) Setup Wizard"
!insertmacro XPUI_DEFAULT XPUI_WELCOMEPAGE_TEXT "This wizard will guide you through the installation of $(^Name).\r\n\r\nIt is recommended that you close all other applications before starting Setup. This will make it possible to update relevant system files without having to reboot your computer.\r\n\r\n"
!insertmacro XPUI_DEFAULT XPUI_WELCOMEPAGE_TITLE "Welcome"
!insertmacro XPUI_DEFAULT XPUI_WELCOMEPAGE_SUBTITLE "Welcome to $(^Name) Setup."
!insertmacro XPUI_DEFAULT XPUI_WELCOMEPAGE_CAPTION " "

!insertmacro XPUI_DEFAULT XPUI_UNWELCOMEPAGE_TEXT_TOP "Welcome to the $(^Name) Uninstall Wizard"
!insertmacro XPUI_DEFAULT XPUI_UNWELCOMEPAGE_TEXT "This wizard will guide you through the uninstallation of $(^Name).\r\n\r\nIt is recommended that you close all other applications before starting Setup. This will make it possible to update relevant system files without having to reboot your computer.\r\n\r\n"
!insertmacro XPUI_DEFAULT XPUI_UNWELCOMEPAGE_TITLE "Welcome"
!insertmacro XPUI_DEFAULT XPUI_UNWELCOMEPAGE_SUBTITLE "Welcome to the $(^Name) Uninstall Wizard."
!insertmacro XPUI_DEFAULT XPUI_UNWELCOMEPAGE_CAPTION " "

; WELCOME PAGE STYLE 2
!insertmacro XPUI_DEFAULT XPUI_WELCOMEPAGESTYLE2_TEXT_TOP "Welcome to the NSIS Setup Wizard for $(^Name)."
!insertmacro XPUI_DEFAULT XPUI_WELCOMEPAGESTYLE2_TEXT "Welcome to $(^Name) Setup.  This will install $(^Name) on your computer."
!insertmacro XPUI_DEFAULT XPUI_WELCOMEPAGESTYLE2_TITLE "Welcome"
!insertmacro XPUI_DEFAULT XPUI_WELCOMEPAGESTYLE2_SUBTITLE "Welcome to $(^Name) Setup."
!insertmacro XPUI_DEFAULT XPUI_WELCOMEPAGESTYLE2_CAPTION " "

!insertmacro XPUI_DEFAULT XPUI_UNWELCOMEPAGESTYLE2_TEXT_TOP "Welcome to the NSIS Uninstall Wizard for $(^Name)."
!insertmacro XPUI_DEFAULT XPUI_UNWELCOMEPAGESTYLE2_TEXT "Welcome to $(^Name) Setup.  This will uninstall $(^Name) from your computer."
!insertmacro XPUI_DEFAULT XPUI_UNWELCOMEPAGESTYLE2_TITLE "Welcome"
!insertmacro XPUI_DEFAULT XPUI_UNWELCOMEPAGESTYLE2_SUBTITLE "Welcome to the $(^Name) Uninstaller."
!insertmacro XPUI_DEFAULT XPUI_UNWELCOMEPAGESTYLE2_CAPTION " "

; LICENSE PAGE
!insertmacro XPUI_DEFAULT XPUI_LICENSEPAGE_CAPTION ": License Agreement"
!insertmacro XPUI_DEFAULT XPUI_LICENSEPAGE_RADIOBUTTONS_TEXT_ACCEPT "I agree to the above terms and conditions"
!insertmacro XPUI_DEFAULT XPUI_LICENSEPAGE_RADIOBUTTONS_TEXT_DECLINE "I do not agree to the above terms and conditions"
!insertmacro XPUI_DEFAULT XPUI_LICENSEPAGE_TEXT_CHECKBOX "I agree to the above terms and conditions"
!insertmacro XPUI_DEFAULT XPUI_LICENSEPAGE_SUBTITLE "Please review the license terms before installing $(^Name)."
!insertmacro XPUI_DEFAULT XPUI_LICENSEPAGE_TITLE "License Agreement"
!insertmacro XPUI_DEFAULT XPUI_LICENSEPAGE_TEXT_TOP "Press Page Down to see the rest of the agreement."
!ifndef XPUI_LICENSEPAGE_RADIOBUTTONS
  !ifndef XPUI_LICENSEPAGE_CHECKBOX
    !insertmacro XPUI_DEFAULT XPUI_LICENSEPAGE_TEXT_BOTTOM "If you accept the terms of the agreement, click I Agree to continue. You must accept the agreement to install $(^Name)."
  !endif
!endif
!ifndef XPUI_LICENSEPAGE_RADIOBUTTONS
  !ifdef XPUI_LICENSEPAGE_CHECKBOX
    !insertmacro XPUI_DEFAULT XPUI_LICENSEPAGE_TEXT_BOTTOM "If you accept the terms of the agreement, check the box below. You must accept the agreement to install $(^Name)."
  !endif
!endif
!ifdef XPUI_LICENSEPAGE_RADIOBUTTONS
  !ifndef XPUI_LICENSEPAGE_CHECKBOX
    !insertmacro XPUI_DEFAULT XPUI_LICENSEPAGE_TEXT_BOTTOM "If you accept the terms of the agreement, select the first option above. You must accept the agreement to install $(^Name)."
  !endif
!endif

!insertmacro XPUI_DEFAULT XPUI_UNLICENSEPAGE_CAPTION ": License Agreement"
!insertmacro XPUI_DEFAULT XPUI_UNLICENSEPAGE_RADIOBUTTONS_TEXT_ACCEPT "I agree to the above terms and conditions"
!insertmacro XPUI_DEFAULT XPUI_UNLICENSEPAGE_RADIOBUTTONS_TEXT_DECLINE "I do not agree to the above terms and conditions"
!insertmacro XPUI_DEFAULT XPUI_UNLICENSEPAGE_TEXT_CHECKBOX "I agree to the above terms and conditions"
!insertmacro XPUI_DEFAULT XPUI_UNLICENSEPAGE_SUBTITLE "Please review the license terms before uninstalling $(^Name)."
!insertmacro XPUI_DEFAULT XPUI_UNLICENSEPAGE_TITLE "License Agreement"
!insertmacro XPUI_DEFAULT XPUI_UNLICENSEPAGE_TEXT_TOP "Press Page Down to see the rest of the agreement."
!ifndef XPUI_UNLICENSEPAGE_RADIOBUTTONS
  !ifndef XPUI_UNLICENSEPAGE_CHECKBOX
    !insertmacro XPUI_DEFAULT XPUI_UNLICENSEPAGE_TEXT_BOTTOM "If you accept the terms of the agreement, click I Agree to continue. You must accept the agreement to uninstall $(^Name)."
  !endif
!endif
!ifndef XPUI_UNLICENSEPAGE_RADIOBUTTONS
  !ifdef XPUI_UNLICENSEPAGE_CHECKBOX
    !insertmacro XPUI_DEFAULT XPUI_UNLICENSEPAGE_TEXT_BOTTOM "If you accept the terms of the agreement, check the box below. You must accept the agreement to uninstall $(^Name)."
  !endif
!endif
!ifdef XPUI_UNLICENSEPAGE_RADIOBUTTONS
  !ifndef XPUI_UNLICENSEPAGE_CHECKBOX
    !insertmacro XPUI_DEFAULT XPUI_UNLICENSEPAGE_TEXT_BOTTOM "If you accept the terms of the agreement, select the first option above. You must accept the agreement to uninstall $(^Name)."
  !endif
!endif

; COMPONENTS PAGE
!insertmacro XPUI_DEFAULT XPUI_COMPONENTSPAGE_CAPTION ": Select Components"
!insertmacro XPUI_DEFAULT XPUI_COMPONENTSPAGE_SUBTITLE "Choose which components of $(^Name) should be installed."
!insertmacro XPUI_DEFAULT XPUI_COMPONENTSPAGE_TITLE "Choose Components"
!insertmacro XPUI_DEFAULT XPUI_COMPONENTSPAGE_TEXT_DESCRIPTION_TITLE "Description"
!insertmacro XPUI_DEFAULT XPUI_COMPONENTSPAGE_TEXT_DESCRIPTION_INFO  "Hover your mouse over a component to see its description"

!insertmacro XPUI_DEFAULT XPUI_UNCOMPONENTSPAGE_CAPTION ": Select Components"
!insertmacro XPUI_DEFAULT XPUI_UNCOMPONENTSPAGE_SUBTITLE "Choose which components of $(^Name) should be uninstalled."
!insertmacro XPUI_DEFAULT XPUI_UNCOMPONENTSPAGE_TITLE "Choose Components"
!insertmacro XPUI_DEFAULT XPUI_UNCOMPONENTSPAGE_TEXT_DESCRIPTION_TITLE "Description"
!insertmacro XPUI_DEFAULT XPUI_UNCOMPONENTSPAGE_TEXT_DESCRIPTION_INFO  "Hover your mouse over a component to see its description"

; DIRECTORY PAGE
!insertmacro XPUI_DEFAULT XPUI_DIRECTORYPAGE_CAPTION ": Select Installation Folder"
!insertmacro XPUI_DEFAULT XPUI_DIRECTORYPAGE_TEXT_TOP "Setup will install $(^Name) in the following folder.$\r$\n$\r$\nTo install in the default folder, leave the text below as-is.  To install in a different folder, enter one below, or click Browse. $_CLICK"
!insertmacro XPUI_DEFAULT XPUI_DIRECTORYPAGE_TEXT_DESTINATION "Installation Directory"
!insertmacro XPUI_DEFAULT XPUI_DIRECTORYPAGE_TEXT_BROWSE "Browse"
!insertmacro XPUI_DEFAULT XPUI_DIRECTORYPAGE_TEXT_BROWSEDIALOG "Please select a folder:"
!insertmacro XPUI_DEFAULT XPUI_DIRECTORYPAGE_TITLE "Choose Install Location"
!insertmacro XPUI_DEFAULT XPUI_DIRECTORYPAGE_SUBTITLE "Choose the folder in which to install $(^Name)."

!insertmacro XPUI_DEFAULT XPUI_UNDIRECTORYPAGE_CAPTION ": Select Installation Folder"
!insertmacro XPUI_DEFAULT XPUI_UNDIRECTORYPAGE_TEXT_TOP "Setup will uninstall $(^Name) from the following folder.$\r$\n$\r$\nTo uninstall from this folder, click Next.  To uninstall from a different folder, enter one below, or click Browse."
!insertmacro XPUI_DEFAULT XPUI_UNDIRECTORYPAGE_TEXT_DESTINATION "Installation Directory"
!insertmacro XPUI_DEFAULT XPUI_UNDIRECTORYPAGE_TEXT_BROWSE "Browse"
!insertmacro XPUI_DEFAULT XPUI_UNDIRECTORYPAGE_TEXT_BROWSEDIALOG "Please select a folder:"
!insertmacro XPUI_DEFAULT XPUI_UNDIRECTORYPAGE_TITLE "Choose Install Location"
!insertmacro XPUI_DEFAULT XPUI_UNDIRECTORYPAGE_SUBTITLE "Choose the folder from which to uninstall $(^Name)."

; START MENU PAGE
!insertmacro XPUI_DEFAULT XPUI_STARTMENUPAGE_CAPTION    ": Start Menu Folder"
!insertmacro XPUI_DEFAULT XPUI_STARTMENUPAGE_TITLE      "Select Start Menu Folder"
!insertmacro XPUI_DEFAULT XPUI_STARTMENUPAGE_SUBTITLE   "Select the folder in which to create Start Menu shortcuts to $(^Name):"
!insertmacro XPUI_DEFAULT XPUI_STARTMENUPAGE_TEXT       "Select the Start Menu folder in which to create shortcuts to $(^Name):"
!insertmacro XPUI_DEFAULT XPUI_STARTMENUPAGE_CHECKBOX   "Don't create a start menu folder"
!insertmacro XPUI_DEFAULT XPUI_STARTMENUPAGE_FOLDER     "$(^Name)"

!insertmacro XPUI_DEFAULT XPUI_UNSTARTMENUPAGE_CAPTION  ": Start Menu Folder"
!insertmacro XPUI_DEFAULT XPUI_UNSTARTMENUPAGE_TITLE    "Select Start Menu Folder"
!insertmacro XPUI_DEFAULT XPUI_UNSTARTMENUPAGE_SUBTITLE "Select the folder from which to remove Start Menu shortcuts:"
!insertmacro XPUI_DEFAULT XPUI_UNSTARTMENUPAGE_TEXT     "Select the Start Menu folder in which to remove shortcuts from:"
!insertmacro XPUI_DEFAULT XPUI_UNSTARTMENUPAGE_CHECKBOX "Don't remove the Start Menu folder"
!insertmacro XPUI_DEFAULT XPUI_UNSTARTMENUPAGE_FOLDER   "$(^Name)"

; INSTALL CONFIRM PAGE
!insertmacro XPUI_DEFAULT XPUI_INSTCONFIRMPAGE_CAPTION ": Confirm Installation"
!insertmacro XPUI_DEFAULT XPUI_INSTCONFIRMPAGE_SUBTITLE "Setup has finished gathering information and is ready to install $(^Name)."
!insertmacro XPUI_DEFAULT XPUI_INSTCONFIRMPAGE_TITLE "Confirm Installation"
!insertmacro XPUI_DEFAULT XPUI_INSTCONFIRMPAGE_TEXT_TOP "Setup is ready to install $(^Name) on your computer."
!insertmacro XPUI_DEFAULT XPUI_INSTCONFIRMPAGE_TEXT_BOTTOM "$_CLICK"

!insertmacro XPUI_DEFAULT XPUI_UNINSTCONFIRMPAGE_CAPTION ": Confirm Installation"
!insertmacro XPUI_DEFAULT XPUI_UNINSTCONFIRMPAGE_SUBTITLE "Setup has finished gathering information and is ready to uninstall $(^Name)."
!insertmacro XPUI_DEFAULT XPUI_UNINSTCONFIRMPAGE_TITLE "Confirm Uninstallation"
!insertmacro XPUI_DEFAULT XPUI_UNINSTCONFIRMPAGE_TEXT_TOP "Setup is ready to uninstall $(^Name) from your computer."
!insertmacro XPUI_DEFAULT XPUI_UNINSTCONFIRMPAGE_TEXT_BOTTOM "Click Uninstall to continue."

; INSTFILES PAGE
!insertmacro XPUI_DEFAULT XPUI_INSTFILESPAGE_CAPTION ": Copying Files"
!insertmacro XPUI_DEFAULT XPUI_INSTFILESPAGE_SUBTITLE "Please wait while $(^Name) is being installed."
!insertmacro XPUI_DEFAULT XPUI_INSTFILESPAGE_TITLE "Installing"
!insertmacro XPUI_DEFAULT XPUI_INSTFILESPAGE_DONE_TITLE "Installation Complete"
!insertmacro XPUI_DEFAULT XPUI_INSTFILESPAGE_DONE_SUBTITLE "All of the components of $(^Name) were successfully copied to your computer."

!insertmacro XPUI_DEFAULT XPUI_UNINSTFILESPAGE_CAPTION ": Uninstalling Files"
!insertmacro XPUI_DEFAULT XPUI_UNINSTFILESPAGE_SUBTITLE "Please wait while $(^Name) is being uninstalled."
!insertmacro XPUI_DEFAULT XPUI_UNINSTFILESPAGE_TITLE "Uninstalling"
!insertmacro XPUI_DEFAULT XPUI_UNINSTFILESPAGE_DONE_TITLE "Uninstallation Complete"
!insertmacro XPUI_DEFAULT XPUI_UNINSTFILESPAGE_DONE_SUBTITLE "All of the components of $(^Name) were successfully removed from your computer."

; INSTALL SUCCESS PAGE
!insertmacro XPUI_DEFAULT XPUI_INSTSUCCESSPAGE_CAPTION ": Installation Successful"
!insertmacro XPUI_DEFAULT XPUI_INSTSUCCESSPAGE_SUBTITLE "Setup has successfully installed $(^Name) on your computer."
!insertmacro XPUI_DEFAULT XPUI_INSTSUCCESSPAGE_TITLE "Installation Complete"
!insertmacro XPUI_DEFAULT XPUI_INSTSUCCESSPAGE_TEXT_TOP    "$(^Name) has been successfully installed."
!insertmacro XPUI_DEFAULT XPUI_INSTSUCCESSPAGE_TEXT_BOTTOM "Click Close to exit."

!insertmacro XPUI_DEFAULT XPUI_UNINSTSUCCESSPAGE_CAPTION ": Uninstallation Successful"
!insertmacro XPUI_DEFAULT XPUI_UNINSTSUCCESSPAGE_SUBTITLE "Setup has successfully uninstalled $(^Name) from your computer."
!insertmacro XPUI_DEFAULT XPUI_UNINSTSUCCESSPAGE_TITLE "Uninstallation Complete"
!insertmacro XPUI_DEFAULT XPUI_UNINSTSUCCESSPAGE_TEXT_TOP    "$(^Name) has been successfully uninstalled."
!insertmacro XPUI_DEFAULT XPUI_UNINSTSUCCESSPAGE_TEXT_BOTTOM "Click Close to exit."

; FINISH PAGE

!insertmacro XPUI_DEFAULT XPUI_FINISHPAGE_TITLE "Installation Complete"
!insertmacro XPUI_DEFAULT XPUI_FINISHPAGE_SUBTITLE "Setup has successfully installed $(^Name) on your computer."
!insertmacro XPUI_DEFAULT XPUI_FINISHPAGE_CAPTION ": Installation Complete"
!insertmacro XPUI_DEFAULT XPUI_FINISHPAGE_TEXT_TOP "Completing the $(^Name) Setup Wizard"
!insertmacro XPUI_DEFAULT XPUI_FINISHPAGE_TEXT_TOP_ALT "NSIS Setup Wizard Complete"
!insertmacro XPUI_DEFAULT XPUI_FINISHPAGE_TEXT_RUN "Setup has successfully installed $(^Name) on your computer.\r\n\r\nWhich actions do you want to perform?"
!insertmacro XPUI_DEFAULT XPUI_FINISHPAGE_TEXT_REBOOT "Setup has finished copying files to your computer.\r\n\r\nTo finish the installation, you must restart your computer.  Do you want to restart your computer now?"
!insertmacro XPUI_DEFAULT XPUI_FINISHPAGE_TEXT "Setup has successfully installed $(^Name) on your computer.\r\n\r\nPlease click $(XPUI_BUTTONTEXT_CLOSE) to exit Setup."
!insertmacro XPUI_DEFAULT XPUI_FINISHPAGE_CHECKBOX_RUN "Run $(^Name) now"
!insertmacro XPUI_DEFAULT XPUI_FINISHPAGE_CHECKBOX_DOCS "View the documentation for $(^Name)"
!insertmacro XPUI_DEFAULT XPUI_FINISHPAGE_RADIOBUTTON_REBOOT "Yes, restart my computer now."
!insertmacro XPUI_DEFAULT XPUI_FINISHPAGE_RADIOBUTTON_NOREBOOT "No, I will restart my computer later."
!insertmacro XPUI_DEFAULT XPUI_FINISHPAGE_REBOOT_MESSAGEBOX "Setup is about to reboot your computer.$\r$\n$\r$\nPlease save and close all open files and documents, and click OK to reboot your computer."

!insertmacro XPUI_DEFAULT XPUI_UNFINISHPAGE_TITLE "Uninstall Complete"
!insertmacro XPUI_DEFAULT XPUI_UNFINISHPAGE_SUBTITLE "Setup has successfully uninstalled $(^Name) from your computer."
!insertmacro XPUI_DEFAULT XPUI_UNFINISHPAGE_CAPTION ": Uninstall Complete"
!insertmacro XPUI_DEFAULT XPUI_UNFINISHPAGE_TEXT_TOP "Completing the $(^Name) Setup Wizard"
!insertmacro XPUI_DEFAULT XPUI_UNFINISHPAGE_TEXT_TOP_ALT "NSIS Setup Wizard Complete"
!insertmacro XPUI_DEFAULT XPUI_UNFINISHPAGE_TEXT_REBOOT "Setup has finished copying files to your computer.\r\n\r\nTo finish the uninstallation, you must restart your computer.  Do you want to restart your computer now?"
!insertmacro XPUI_DEFAULT XPUI_UNFINISHPAGE_TEXT_RUN "Setup has successfully uninstalled $(^Name) from your computer.\r\n\r\nWhich actions do you want to perform?"
!insertmacro XPUI_DEFAULT XPUI_UNFINISHPAGE_TEXT "Setup has successfully uninstalled $(^Name) from your computer.\r\n\r\nPlease click $(XPUI_BUTTONTEXT_CLOSE) to exit Setup."
!insertmacro XPUI_DEFAULT XPUI_UNFINISHPAGE_RADIOBUTTON_REBOOT "Yes, restart my computer now."
!insertmacro XPUI_DEFAULT XPUI_UNFINISHPAGE_RADIOBUTTON_NOREBOOT "No, I will restart my computer later."

; ABORT PAGE
!insertmacro XPUI_DEFAULT XPUI_ABORTPAGE_TEXT_TOP "NSIS Setup Wizard Complete"
!insertmacro XPUI_DEFAULT XPUI_ABORTPAGE_TEXT "The wizard was interrupted before $(^Name) could be completely installed.\r\n\r\nYour system has not been modified.  To install this program at a later time, please run this\r\nwizard again.\r\n\r\n\r\n\r\n\r\nPlease click Close to exit the Setup Wizard."
!insertmacro XPUI_DEFAULT XPUI_ABORTPAGE_TITLE "Installation Incomplete"
!insertmacro XPUI_DEFAULT XPUI_ABORTPAGE_SUBTITLE "$(^Name) Setup was not completed successfully."
!insertmacro XPUI_DEFAULT XPUI_ABORTPAGE_CAPTION ": Setup Cancelled"

!insertmacro XPUI_DEFAULT XPUI_UNABORTPAGE_TEXT_TOP "NSIS Uninstall Wizard Complete"
!insertmacro XPUI_DEFAULT XPUI_UNABORTPAGE_TEXT "The wizard was interrupted before $(^Name) could be completely uninstalled.\r\n\r\nYour system has not been modified.  To uninstall this program at a later time, please run this wizard again.\r\n\r\n\r\n\r\n\r\nPlease click Close to exit the Setup Wizard."
!insertmacro XPUI_DEFAULT XPUI_UNABORTPAGE_TITLE "Uninstallation Incomplete"
!insertmacro XPUI_DEFAULT XPUI_UNABORTPAGE_SUBTITLE "$(^Name) Setup was not completed successfully."
!insertmacro XPUI_DEFAULT XPUI_UNABORTPAGE_CAPTION ": Setup Cancelled"

; +-----------+
; | UNINSTALL |
; +-----------+

; MOST OF THE UNINSTALL PAGES ARE TAKEN CARE OF USING THE PAGE MODE SYSTEM
; THE XPUI CONFIRM, UNINSTFILES, AND SUCCESS PAGES USE THE PAGE MODE SYSTEM,
; BUT THE NSIS-STYLE UNINSTALL CONFIRM PAGE SIMPLY USES A NON-PAGE-MODE METHOD.

; UNINST CONFIRM PAGE (NSIS)
!insertmacro XPUI_DEFAULT XPUI_UNINSTCONFIRMPAGE_NSIS_CAPTION ": Confirm Uninstall"
!insertmacro XPUI_DEFAULT XPUI_UNINSTCONFIRMPAGE_NSIS_SUBTITLE "Remove $(^Name) from your computer"
!insertmacro XPUI_DEFAULT XPUI_UNINSTCONFIRMPAGE_NSIS_TITLE "Uninstall $(^Name)"
!insertmacro XPUI_DEFAULT XPUI_UNINSTCONFIRMPAGE_NSIS_TEXT_TOP "$(^Name) will be uninstalled from the following folder.  Click Next to start the uninstallation."
!insertmacro XPUI_DEFAULT XPUI_UNINSTCONFIRMPAGE_NSIS_TEXT_FOLDER "Uninstalling from:"

!insertmacro XPUI_LANGUAGEFILE_END
