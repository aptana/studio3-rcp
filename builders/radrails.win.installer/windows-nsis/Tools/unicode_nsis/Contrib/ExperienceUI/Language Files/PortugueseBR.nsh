;ExperienceUI for NSIS - Language File
;Compatible with "Bryce" M1 and later

;Language: Brazilian Portuguese (1046)
;By Jenner Modesto <jennermodesto@gmail.com>

;--------------------------------

!insertmacro XPUI_LANGUAGEFILE_BEGIN "PortugueseBR"

; Use only ASCII characters (if this is not possible, use the English name)
!define XPUI_LANGNAME "Português do Brasil"

; BUTTONS
!insertmacro XPUI_DEFAULT XPUI_BUTTONTEXT_NEXT   Avançar
!insertmacro XPUI_DEFAULT XPUI_BUTTONTEXT_BACK   Voltar
!insertmacro XPUI_DEFAULT XPUI_BUTTONTEXT_CANCEL Cancelar
!insertmacro XPUI_DEFAULT XPUI_BUTTONTEXT_CLOSE  Fechar

!insertmacro XPUI_DEFAULT XPUI_ABORTWARNING_TEXT "Você deseja realmente finalizar a instalação do $(^Name)?"

; +---------+
; | INSTALL |
; +---------+

; WELCOME PAGE
!insertmacro XPUI_DEFAULT XPUI_WELCOMEPAGE_TEXT_TOP "Bem-vindo ao Assistente de Instalação de $(^Name)"
!insertmacro XPUI_DEFAULT XPUI_WELCOMEPAGE_TEXT "Esse Assistente irá orientá-lo durante o processo de instalação de $(^Name).\r\n\r\nÉ recomendado que todos os outros aplicativos sejam fechados antes de iniciar o Assistente. Isso tornará possível a atualização de arquivos de sistema sem a necessidade de reiniciar seu computador.\r\n\r\n"
!insertmacro XPUI_DEFAULT XPUI_WELCOMEPAGE_TITLE "Bem-vindo"
!insertmacro XPUI_DEFAULT XPUI_WELCOMEPAGE_SUBTITLE "Bem-vindo ao Assistente de Instalação de $(^Name)."
!insertmacro XPUI_DEFAULT XPUI_WELCOMEPAGE_CAPTION " "

!insertmacro XPUI_DEFAULT XPUI_UNWELCOMEPAGE_TEXT_TOP "Bem-vindo ao assistente de desinstalação de $(^Name)"
!insertmacro XPUI_DEFAULT XPUI_UNWELCOMEPAGE_TEXT "Esse Assistente irá orientá-lo durante o processo de desinstalação de $(^Name).\r\n\r\nÉ recomendado que todos os outros aplicativos sejam fechados antes de iniciar o Assistente. Isso tornará possível a atualização de arquivos de sistema sem a necessidade de reiniciar seu computador.\r\n\r\n"
!insertmacro XPUI_DEFAULT XPUI_UNWELCOMEPAGE_TITLE "Bem-vindo"
!insertmacro XPUI_DEFAULT XPUI_UNWELCOMEPAGE_SUBTITLE "Bem-vindo ao Assistente de Desinstalação de $(^Name)."
!insertmacro XPUI_DEFAULT XPUI_UNWELCOMEPAGE_CAPTION " "

; WELCOME PAGE STYLE 2
!insertmacro XPUI_DEFAULT XPUI_WELCOMEPAGESTYLE2_TEXT_TOP "Bem-vindo ao Assistente de Instalação de $(^Name)"
!insertmacro XPUI_DEFAULT XPUI_WELCOMEPAGESTYLE2_TEXT "Bem-vindo à Instalação de $(^Name).  $(^Name) será instalado em seu computador."
!insertmacro XPUI_DEFAULT XPUI_WELCOMEPAGESTYLE2_TITLE "Bem-vindo"
!insertmacro XPUI_DEFAULT XPUI_WELCOMEPAGESTYLE2_SUBTITLE "Bem-vindo à Instalação de $(^Name)."
!insertmacro XPUI_DEFAULT XPUI_WELCOMEPAGESTYLE2_CAPTION " "

!insertmacro XPUI_DEFAULT XPUI_UNWELCOMEPAGESTYLE2_TEXT_TOP "Bem-vindo ao Assistente do NSIS de Desinstalação de $(^Name)."
!insertmacro XPUI_DEFAULT XPUI_UNWELCOMEPAGESTYLE2_TEXT "Bem-vindo à Desinstalação de $(^Name).  $(^Name) será desinstalado do seu computador."
!insertmacro XPUI_DEFAULT XPUI_UNWELCOMEPAGESTYLE2_TITLE "Bem-vindo"
!insertmacro XPUI_DEFAULT XPUI_UNWELCOMEPAGESTYLE2_SUBTITLE "Bem-vindo à Desinstalação de $(^Name)."
!insertmacro XPUI_DEFAULT XPUI_UNWELCOMEPAGESTYLE2_CAPTION " "

; LICENSE PAGE
!insertmacro XPUI_DEFAULT XPUI_LICENSEPAGE_CAPTION ": Acordo de Licença"
!insertmacro XPUI_DEFAULT XPUI_LICENSEPAGE_RADIOBUTTONS_TEXT_ACCEPT "Eu concordo com os termos e condições acima"
!insertmacro XPUI_DEFAULT XPUI_LICENSEPAGE_RADIOBUTTONS_TEXT_DECLINE "Eu não concordo com os termos e condições acima"
!insertmacro XPUI_DEFAULT XPUI_LICENSEPAGE_TEXT_CHECKBOX "Eu concordo com os termos e condições acima"
!insertmacro XPUI_DEFAULT XPUI_LICENSEPAGE_SUBTITLE "Por favor, leia os termos de licença abaixo antes de desinstalar $(^Name)."
!insertmacro XPUI_DEFAULT XPUI_LICENSEPAGE_TITLE "Acordo de Licença"
!insertmacro XPUI_DEFAULT XPUI_LICENSEPAGE_TEXT_TOP "Pressione Page Down ou use a barra de rolagem para ver o resto do acordo."
!ifndef XPUI_LICENSEPAGE_RADIOBUTTONS
  !ifndef XPUI_LICENSEPAGE_CHECKBOX
    !insertmacro XPUI_DEFAULT XPUI_LICENSEPAGE_TEXT_BOTTOM "Se você aceita os termos do acordo, clique em Eu Concordo para continuar. Você tem que aceitar o acordo para instalar $(^Name)."
  !endif
!endif
!ifndef XPUI_LICENSEPAGE_RADIOBUTTONS
  !ifdef XPUI_LICENSEPAGE_CHECKBOX
    !insertmacro XPUI_DEFAULT XPUI_LICENSEPAGE_TEXT_BOTTOM "Se você aceita os termos do acordo, marque a caixa abaixo. Você tem que aceitar o acordo para instalar $(^Name)."
  !endif
!endif
!ifdef XPUI_LICENSEPAGE_RADIOBUTTONS
  !ifndef XPUI_LICENSEPAGE_CHECKBOX
    !insertmacro XPUI_DEFAULT XPUI_LICENSEPAGE_TEXT_BOTTOM "Se você aceita os termos do acordo, selecione a primeira opção acima. Você tem que aceitar o acordo para instalar $(^Name)."
  !endif
!endif

!insertmacro XPUI_DEFAULT XPUI_UNLICENSEPAGE_CAPTION ": Acordo de Licença"
!insertmacro XPUI_DEFAULT XPUI_UNLICENSEPAGE_RADIOBUTTONS_TEXT_ACCEPT "Eu concordo com os termos e condições acima"
!insertmacro XPUI_DEFAULT XPUI_UNLICENSEPAGE_RADIOBUTTONS_TEXT_DECLINE "Eu não concordo com os termos e condições acima"
!insertmacro XPUI_DEFAULT XPUI_UNLICENSEPAGE_TEXT_CHECKBOX "Eu concordo com os termos e condições acima"
!insertmacro XPUI_DEFAULT XPUI_UNLICENSEPAGE_SUBTITLE "Por favor, leia os termos de licença abaixo antes de desinstalar $(^Name)."
!insertmacro XPUI_DEFAULT XPUI_UNLICENSEPAGE_TITLE "Acordo de Licença"
!insertmacro XPUI_DEFAULT XPUI_UNLICENSEPAGE_TEXT_TOP "Pressione Page Down ou use a barra de rolagem para ver o resto do acordo."
!ifndef XPUI_UNLICENSEPAGE_RADIOBUTTONS
  !ifndef XPUI_UNLICENSEPAGE_CHECKBOX
    !insertmacro XPUI_DEFAULT XPUI_UNLICENSEPAGE_TEXT_BOTTOM "Se você aceita os termos do acordo, clique em Eu Concordo para continuar. Você tem que aceitar o acordo para desinstalar $(^Name)."
  !endif
!endif
!ifndef XPUI_UNLICENSEPAGE_RADIOBUTTONS
  !ifdef XPUI_UNLICENSEPAGE_CHECKBOX
    !insertmacro XPUI_DEFAULT XPUI_UNLICENSEPAGE_TEXT_BOTTOM "Se você aceita os termos do acordo, marque a caixa abaixo. Você tem que aceitar o acordo para desinstalar $(^Name)."
  !endif
!endif
!ifdef XPUI_UNLICENSEPAGE_RADIOBUTTONS
  !ifndef XPUI_UNLICENSEPAGE_CHECKBOX
    !insertmacro XPUI_DEFAULT XPUI_UNLICENSEPAGE_TEXT_BOTTOM "Se você aceita os termos do acordo, selecione a primeira opção acima. Você tem que aceitar o acordo para desinstalar $(^Name)."
  !endif
!endif

; COMPONENTS PAGE
!insertmacro XPUI_DEFAULT XPUI_COMPONENTSPAGE_CAPTION ": Escolha os Componentes"
!insertmacro XPUI_DEFAULT XPUI_COMPONENTSPAGE_SUBTITLE "Escolha quais componentes de $(^Name) devem ser instalados."
!insertmacro XPUI_DEFAULT XPUI_COMPONENTSPAGE_TITLE "Escolha os Componentes"
!insertmacro XPUI_DEFAULT XPUI_COMPONENTSPAGE_TEXT_DESCRIPTION_TITLE "Descrição"
!insertmacro XPUI_DEFAULT XPUI_COMPONENTSPAGE_TEXT_DESCRIPTION_INFO  "Passe o mouse sobre um componente para ver sua descrição"

!insertmacro XPUI_DEFAULT XPUI_UNCOMPONENTSPAGE_CAPTION ": Selecione os Componentes"
!insertmacro XPUI_DEFAULT XPUI_UNCOMPONENTSPAGE_SUBTITLE "Escolha quais componentes de $(^Name) devem ser desinstalados."
!insertmacro XPUI_DEFAULT XPUI_UNCOMPONENTSPAGE_TITLE "Escolha os Componentes"
!insertmacro XPUI_DEFAULT XPUI_UNCOMPONENTSPAGE_TEXT_DESCRIPTION_TITLE "Descrição"
!insertmacro XPUI_DEFAULT XPUI_UNCOMPONENTSPAGE_TEXT_DESCRIPTION_INFO  "Passe o mouse sobre um componente para ver sua descrição"

; DIRECTORY PAGE
!insertmacro XPUI_DEFAULT XPUI_DIRECTORYPAGE_CAPTION ": Selecione o Diretório de Instalação"
!insertmacro XPUI_DEFAULT XPUI_DIRECTORYPAGE_TEXT_TOP "O Assistente irá instalar $(^Name) no diretório abaixo.$\n$\nPara instalar nesse diretório, clique em Avançar.  Para instalar em outro diretório, digite manualmente ou clique em Procurar.  $_CLICK"
!insertmacro XPUI_DEFAULT XPUI_DIRECTORYPAGE_TEXT_DESTINATION "Diretório de Instalação"
!insertmacro XPUI_DEFAULT XPUI_DIRECTORYPAGE_TEXT_BROWSE "Procurar"
!insertmacro XPUI_DEFAULT XPUI_DIRECTORYPAGE_TEXT_BROWSEDIALOG "Por favor, selecione um diretório:"
!insertmacro XPUI_DEFAULT XPUI_DIRECTORYPAGE_TITLE "Escolha o Local de Instalação"
!insertmacro XPUI_DEFAULT XPUI_DIRECTORYPAGE_SUBTITLE "Escolha o diretório no qual será instalado $(^Name)."

!insertmacro XPUI_DEFAULT XPUI_UNDIRECTORYPAGE_CAPTION ": Selecione o Diretório de Instalação"
!insertmacro XPUI_DEFAULT XPUI_UNDIRECTORYPAGE_TEXT_TOP "O Assistente irá desinstalar $(^Name) do diretório abaixo.$\n$\nPara desinstalar desse diretório, clique em Avançar.  Para desinstalar de outro diretório, digite manualmente ou clique em Procurar. $_CLICK"
!insertmacro XPUI_DEFAULT XPUI_UNDIRECTORYPAGE_TEXT_DESTINATION "Diretório de Instalação"
!insertmacro XPUI_DEFAULT XPUI_UNDIRECTORYPAGE_TEXT_BROWSE "Procurar"
!insertmacro XPUI_DEFAULT XPUI_UNDIRECTORYPAGE_TEXT_BROWSEDIALOG "Por favor, selecione um diretório:"
!insertmacro XPUI_DEFAULT XPUI_UNDIRECTORYPAGE_TITLE "Escolha o Local de Instalação"
!insertmacro XPUI_DEFAULT XPUI_UNDIRECTORYPAGE_SUBTITLE "Escolha o diretório do qual será desinstalado $(^Name)."

; START MENU PAGE
!insertmacro XPUI_DEFAULT XPUI_STARTMENUPAGE_CAPTION    ": Pasta do Menu Iniciar"
!insertmacro XPUI_DEFAULT XPUI_STARTMENUPAGE_TITLE      "Selecione a Pasta do Menu Iniciar"
!insertmacro XPUI_DEFAULT XPUI_STARTMENUPAGE_SUBTITLE   "Selecione a pasta na qual serão criados atalhos no Menu Iniciar para $(^Name):"
!insertmacro XPUI_DEFAULT XPUI_STARTMENUPAGE_TEXT       "Selecione a pasta do Menu Iniciar na qual serão criados os atalhos para $(^Name):"
!insertmacro XPUI_DEFAULT XPUI_STARTMENUPAGE_CHECKBOX   "Não criar pasta no Menu Iniciar"
!insertmacro XPUI_DEFAULT XPUI_STARTMENUPAGE_FOLDER     "$(^Name)"

!insertmacro XPUI_DEFAULT XPUI_UNSTARTMENUPAGE_CAPTION  ": Pasta do Menu Iniciar"
!insertmacro XPUI_DEFAULT XPUI_UNSTARTMENUPAGE_TITLE    "Selecione a Pasta do Menu Iniciar"
!insertmacro XPUI_DEFAULT XPUI_UNSTARTMENUPAGE_SUBTITLE "Selecione a pasta da qual serão removidos os atalhos do Menu Iniciar:"
!insertmacro XPUI_DEFAULT XPUI_UNSTARTMENUPAGE_TEXT     "Selecione a pasta do Menu Iniciar da qual serão removidos os atalhos:"
!insertmacro XPUI_DEFAULT XPUI_UNSTARTMENUPAGE_CHECKBOX "Não remover a pasta do Menu Iniciar"
!insertmacro XPUI_DEFAULT XPUI_UNSTARTMENUPAGE_FOLDER   "$(^Name)"

; INSTALL CONFIRM PAGE
!insertmacro XPUI_DEFAULT XPUI_INSTCONFIRMPAGE_CAPTION ": Confirmar a Instalação"
!insertmacro XPUI_DEFAULT XPUI_INSTCONFIRMPAGE_SUBTITLE "O Assistente terminou de reunir informações e está pronto para instalar $(^Name)."
!insertmacro XPUI_DEFAULT XPUI_INSTCONFIRMPAGE_TITLE "Confirmar a Instalação"
!insertmacro XPUI_DEFAULT XPUI_INSTCONFIRMPAGE_TEXT_TOP "O Assistente está pronto para instalar $(^Name) em seu computador."
!insertmacro XPUI_DEFAULT XPUI_INSTCONFIRMPAGE_TEXT_BOTTOM "$_CLICK"

!insertmacro XPUI_DEFAULT XPUI_UNINSTCONFIRMPAGE_CAPTION ": Confirmar a Desinstalação"
!insertmacro XPUI_DEFAULT XPUI_UNINSTCONFIRMPAGE_SUBTITLE "O Assistente terminou de reunir informações e está pronto para desinstalar $(^Name)."
!insertmacro XPUI_DEFAULT XPUI_UNINSTCONFIRMPAGE_TITLE "Confirmar a desinstalação"
!insertmacro XPUI_DEFAULT XPUI_UNINSTCONFIRMPAGE_TEXT_TOP "O Assistente está pronto para desinstalar $(^Name) do seu computador."
!insertmacro XPUI_DEFAULT XPUI_UNINSTCONFIRMPAGE_TEXT_BOTTOM "$_CLICK"

; INSTFILES PAGE
!insertmacro XPUI_DEFAULT XPUI_INSTFILESPAGE_CAPTION ": Copiando Arquivos"
!insertmacro XPUI_DEFAULT XPUI_INSTFILESPAGE_SUBTITLE "Por favor, aguarde enquanto $(^Name) está sendo instalado."
!insertmacro XPUI_DEFAULT XPUI_INSTFILESPAGE_TITLE "Instalando"
!insertmacro XPUI_DEFAULT XPUI_INSTFILESPAGE_DONE_TITLE "Instalação Completada"
!insertmacro XPUI_DEFAULT XPUI_INSTFILESPAGE_DONE_SUBTITLE "A instalação foi concluída com sucesso."

!insertmacro XPUI_DEFAULT XPUI_UNINSTFILESPAGE_CAPTION ": Desinstalando Arquivos"
!insertmacro XPUI_DEFAULT XPUI_UNINSTFILESPAGE_SUBTITLE "Por favor, aguarde enquanto $(^Name) está sendo desinstalado."
!insertmacro XPUI_DEFAULT XPUI_UNINSTFILESPAGE_TITLE "Desinstalando"
!insertmacro XPUI_DEFAULT XPUI_UNINSTFILESPAGE_DONE_TITLE "Deinstalação Completada"
!insertmacro XPUI_DEFAULT XPUI_UBINSTFILESPAGE_DONE_SUBTITLE "A deinstalação foi concluída com sucesso."

; INSTALL SUCCESS PAGE
!insertmacro XPUI_DEFAULT XPUI_INSTSUCCESSPAGE_CAPTION ": Instalação Bem Sucedida"
!insertmacro XPUI_DEFAULT XPUI_INSTSUCCESSPAGE_SUBTITLE "O Assistente instalou $(^Name) em seu computador com êxito."
!insertmacro XPUI_DEFAULT XPUI_INSTSUCCESSPAGE_TITLE "Instalação Completa"
!insertmacro XPUI_DEFAULT XPUI_INSTSUCCESSPAGE_TEXT_TOP    "$(^Name) foi instalado com êxito."
!insertmacro XPUI_DEFAULT XPUI_INSTSUCCESSPAGE_TEXT_BOTTOM "Clique em Fechar para sair."

!insertmacro XPUI_DEFAULT XPUI_UNINSTSUCCESSPAGE_CAPTION ": Desinstalação Bem Sucedida"
!insertmacro XPUI_DEFAULT XPUI_UNINSTSUCCESSPAGE_SUBTITLE "O Assistente desinstalou $(^Name) do seu computador com êxito."
!insertmacro XPUI_DEFAULT XPUI_UNINSTSUCCESSPAGE_TITLE "Desinstalação Completa"
!insertmacro XPUI_DEFAULT XPUI_UNINSTSUCCESSPAGE_TEXT_TOP    "$(^Name) foi desinstalado com êxito."
!insertmacro XPUI_DEFAULT XPUI_UNINSTSUCCESSPAGE_TEXT_BOTTOM "Clique em Fechar para sair."

; FINISH PAGE
!insertmacro XPUI_DEFAULT XPUI_FINISHPAGE_TITLE "Instalação Completa"
!insertmacro XPUI_DEFAULT XPUI_FINISHPAGE_SUBTITLE "O Assistente instalou $(^Name) em seu computador com êxito."
!insertmacro XPUI_DEFAULT XPUI_FINISHPAGE_CAPTION ": Instalação Completa"
!insertmacro XPUI_DEFAULT XPUI_FINISHPAGE_TEXT_TOP "Completando o Assistente de Instalação de $(^Name)"
!insertmacro XPUI_DEFAULT XPUI_FINISHPAGE_TEXT_TOP_ALT "Assistente do NSIS de Instalação Terminado"
!insertmacro XPUI_DEFAULT XPUI_FINISHPAGE_TEXT_RUN "O Assistente instalou $(^Name) em seu computador com êxito.\r\n\r\nO que você deseja executar?"
!insertmacro XPUI_DEFAULT XPUI_FINISHPAGE_TEXT_REBOOT "O Assistente terminou de copiar os arquivos para o seu computador.\r\n\r\nÉ preciso reiniciar seu computador para terminar a instalação.  Você quer reiniciar seu computador agora?"
!insertmacro XPUI_DEFAULT XPUI_FINISHPAGE_TEXT "O Assistente instalou $(^Name) em seu computador com êxito.\r\n\r\nPor favor, clique em Fechar para sair do Assistente."
!insertmacro XPUI_DEFAULT XPUI_FINISHPAGE_CHECKBOX_RUN "Executar $(^Name) agora"
!insertmacro XPUI_DEFAULT XPUI_FINISHPAGE_CHECKBOX_DOCS "Ver a documentação de $(^Name)"
!insertmacro XPUI_DEFAULT XPUI_FINISHPAGE_RADIOBUTTON_REBOOT "Sim, reinicie meu computador agora."
!insertmacro XPUI_DEFAULT XPUI_FINISHPAGE_RADIOBUTTON_NOREBOOT "Não, eu reiniciarei meu computador mais tarde."
!insertmacro XPUI_DEFAULT XPUI_FINISHPAGE_REBOOT_MESSAGEBOX "O Assistente está prestes a reiniciar seu computador.$\n$\nPor favor, salve e feche todos os arquivos e documentos abertos e clique em OK para reiniciar seu computador."

!insertmacro XPUI_DEFAULT XPUI_UNFINISHPAGE_TITLE "Desinstalação Completa"
!insertmacro XPUI_DEFAULT XPUI_UNFINISHPAGE_SUBTITLE "O Assistente desinstalou $(^Name) do seu computador com êxito."
!insertmacro XPUI_DEFAULT XPUI_UNFINISHPAGE_CAPTION ": Desinstalação Completa"
!insertmacro XPUI_DEFAULT XPUI_UNFINISHPAGE_TEXT_TOP "Terminando o Assistente de Instalação de $(^Name)"
!insertmacro XPUI_DEFAULT XPUI_UNFINISHPAGE_TEXT_TOP_ALT "Assistente do NSIS de Instalação Terminado"
!insertmacro XPUI_DEFAULT XPUI_UNFINISHPAGE_TEXT_REBOOT "O Assistente terminou de copiar os arquivos para o seu computador.\r\n\r\nÉ preciso reiniciar seu computador para terminar a desinstalação.  Você quer reiniciar seu computador agora?"
!insertmacro XPUI_DEFAULT XPUI_UNFINISHPAGE_TEXT "O Assistente desinstalou $(^Name) do seu computador com êxito.\r\n\r\nPor favor, clique em Fechar para sair do Assistente."
!insertmacro XPUI_DEFAULT XPUI_UNFINISHPAGE_RADIOBUTTON_REBOOT "Sim, reinicie me computador agora."
!insertmacro XPUI_DEFAULT XPUI_UNFINISHPAGE_RADIOBUTTON_NOREBOOT "Não, eu reiniciarei meu computador mais tarde."

; ABORT PAGE
!insertmacro XPUI_DEFAULT XPUI_ABORTPAGE_TEXT_TOP "Assistente do NSIS de Instalação Terminado"
!insertmacro XPUI_DEFAULT XPUI_ABORTPAGE_TEXT "O assistente foi interrompido antes que $(^Name) pudesse ser completamente instalado.\r\n\r\nSeu sistema não foi modificado.  Para instalar esse programa mais tarde, execute o assistente novamente.\r\n\r\n\r\n\r\n\r\nPor favor, clique em $(XPUI_BUTTONTEXT_CLOSE) para sair do Assistente."
!insertmacro XPUI_DEFAULT XPUI_ABORTPAGE_TITLE "Instalação Incompleta"
!insertmacro XPUI_DEFAULT XPUI_ABORTPAGE_SUBTITLE "Instalação de $(^Name) não teve êxito."
!insertmacro XPUI_DEFAULT XPUI_ABORTPAGE_CAPTION ": Instalação Cancelada"

!insertmacro XPUI_DEFAULT XPUI_UNABORTPAGE_TEXT_TOP "Assistente do NSIS de Desinstalação Terminado"
!insertmacro XPUI_DEFAULT XPUI_UNABORTPAGE_TEXT "O assistente foi interrompido antes que $(^Name) pudesse ser completamente desinstalado.$\n$\nSeu sistema não foi modificado.  Para desinstalar esse programa mais tarde, execute o assistente novamente.\r\n\r\n\r\n\r\n\r\nPor favor, clique em $(XPUI_BUTTONTEXT_CLOSE) para sair do Assistente."
!insertmacro XPUI_DEFAULT XPUI_UNABORTPAGE_TITLE "Desinstalação Incompleta"
!insertmacro XPUI_DEFAULT XPUI_UNABORTPAGE_SUBTITLE "Desinstalação de $(^Name) não teve êxito."
!insertmacro XPUI_DEFAULT XPUI_UNABORTPAGE_CAPTION ": Desinstalação Cancelada"

; +-----------+
; | UNINSTALL |
; +-----------+

; MOST OF THE UNINSTALL PAGES ARE TAKEN CARE OF USING THE PAGE MODE SYSTEM
; THE XPUI CONFIRM, UNINSTFILES, AND SUCCESS PAGES USE THE PAGE MODE SYSTEM,
; BUT THE NSIS-STYLE UNINSTALL CONFIRM PAGE SIMPLY USES A NON-PAGE-MODE METHOD.

; UNINST CONFIRM PAGE (NSIS)
!insertmacro XPUI_DEFAULT XPUI_UNINSTCONFIRMPAGE_NSIS_CAPTION ": Confirmar Desinstalação"
!insertmacro XPUI_DEFAULT XPUI_UNINSTCONFIRMPAGE_NSIS_SUBTITLE "Remover $(^Name) do seu computador"
!insertmacro XPUI_DEFAULT XPUI_UNINSTCONFIRMPAGE_NSIS_TITLE "Desinstalar $(^Name)"
!insertmacro XPUI_DEFAULT XPUI_UNINSTCONFIRMPAGE_NSIS_TEXT_TOP "$(^Name) será desinstalado.  Clique em Desinstalar para iniciar a desinstalação."
!insertmacro XPUI_DEFAULT XPUI_UNINSTCONFIRMPAGE_NSIS_TEXT_FOLDER "Desinstalando de:"

!insertmacro XPUI_LANGUAGEFILE_END
