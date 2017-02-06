#! groovy
@Library('pipeline-build@rcp') _

timestamps() {
	def stream = 'nightly'
	def gitCommit = ''
	node('keystore && linux && ant && eclipse && jdk') {
		try {
			stage('Checkout') {
				checkout scm
				gitCommit = sh(returnStdout: true, script: 'git rev-parse HEAD').trim()
				// TODO Just stash everything and unstash after first build?
			}

			def studio3Repo = "file://${env.WORKSPACE}/studio3-core/dist/"
			def phpRepo = "file://${env.WORKSPACE}/studio3-php/dist/"
			def pydevRepo = "file://${env.WORKSPACE}/studio3-pydev/dist/"
			def rubyRepo = "file://${env.WORKSPACE}/studio3-ruby/dist/"

			// Feature
			// FIXME Specify output directory as dist/plugin
			buildPlugin('Feature Build') {
				dependencies = [
					'studio3-core': 'Studio/studio3',
					'studio3-php': 'Studio/studio3-php',
					'studio3-pydev': 'Studio/Pydev',
					'studio3-ruby': 'Studio/studio3-ruby'
				]
				builder = 'com.aptana.studio.build'
				outputDir = 'plugin'
				properties = [
					'studio3.p2.repo': studio3Repo,
					'php.p2.repo': phpRepo,
					'pydev.p2.repo': pydevRepo,
					'radrails.p2.repo': rubyRepo
				]
			}

			stage('Clean') {
				// set stream for windows installer later
				if (env.BRANCH_NAME.equals('master')) {
					stream = 'rc'
				} else if (env.BRANCH_NAME.equals('release')) {
					stream = 'beta'
				}
				// Clean everything but dist dir
				sh 'git clean -fdx -e plugin/'
				// Force checking out the same rev we started with
				sh "git checkout -f ${gitCommit}"
			}
			def studio3FeatureRepo = "file://${env.WORKSPACE}/plugin/"

			// RCP
			buildPlugin('RCP Build') {
				dependencies = [:]
				builder = 'com.aptana.rcp.build'
				outputDir = 'rcp'
				properties = ['studio3-feature.p2.repo': studio3FeatureRepo]
			}

			stage('Stash') {
				stash name: 'winZip', includes: 'rcp/studio3.win32.win32.x86.zip'
				stash name: 'winBuilder', includes: 'builders/com.aptana.win.installer/**/*'
				stash name: 'macZip', includes: 'rcp/studio3.macosx.cocoa.x86_64.zip'
				stash name: 'macBuilder', includes: 'builders/com.aptana.mac.installer/**/*'
			}
		} catch (e) {
			// if any exception occurs, mark the build as failed
			currentBuild.result = 'FAILURE'
			throw e
		} finally {
			step([$class: 'WsCleanup', notFailBuild: true])
		}
	} // end node

	stage('Installers') {
		parallel(
			'Windows Installer': {
				node('windows && advanced_installer && ant') {
					unstash 'winZip'
					unstash 'winBuilder'

					timeout(20) {
						withEnv(["PATH+ANT=${tool name: 'Ant 1.9.2', type: 'ant'}\\bin"]) {
							bat "ant -Dwin.source.url=file:///${env.WORKSPACE}/rcp/studio3.win32.win32.x86.zip -f builders/com.aptana.win.installer/build.xml unpack-archives"

							// FIXME I don't think we sign the installer!
							// 'password': '$STOREPASS',
							def propertiesContent = """msi.source.url=http://preview.appcelerator.com/aptana/studio3/standalone/install/${stream}/AptanaStudio.msi
	installer.command=C:\\\\Program Files (x86)\\\\Caphyon\\\\Advanced Installer 11.8\\\\bin\\\\x86\\\\AdvancedInstaller.com
	"""
							writeFile file: 'override.properties', text: propertiesContent
							bat "ant -propertyfile override.properties -f builders/com.aptana.win.installer/build.xml main"
						} // withEnv
						bat 'rename dist win'
					} // timeout
					archiveArtifacts artifacts: 'win/*.exe, win/*.msi, win/*.cab'
					step([$class: 'WsCleanup', notFailBuild: true])
				}
			},
			'Mac Installer': {
				node('osx && packages && ant && certs') {
					unstash 'macZip'
					unstash 'macBuilder'

					timeout(10) {
						withEnv(["PATH+ANT=${tool name: 'Ant 1.9.2', type: 'ant'}/bin"]) {
							sh "ant -Dmac.source.url=file://${env.WORKSPACE}/rcp/studio3.macosx.cocoa.x86_64.zip -f builders/com.aptana.mac.installer/build.xml"
						}
						// Move DMG to new 'mac' directory so merged artifacts get separated nicely
						sh 'mkdir mac'
						sh 'mv builders/com.aptana.mac.installer/staging/*.dmg mac'
					}
					// TODO Check for textFinder('code object is not signed at all', '', true)
					archiveArtifacts artifacts: 'mac/*.dmg'
					step([$class: 'WsCleanup', notFailBuild: true])
				}
			}
		)
	}
}
