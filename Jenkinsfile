#! groovy
@Library('pipeline-build@rcp') _

def stream = 'nightly'
node('keystore && linux && ant && eclipse && jdk') {
	try {
		def studio3Repo = "file://${env.WORKSPACE}/studio3-core/dist/"
		def phpRepo = "file://${env.WORKSPACE}/studio3-php/dist/"
		def pydevRepo = "file://${env.WORKSPACE}/studio3-pydev/dist/"
		def rubyRepo = "file://${env.WORKSPACE}/studio3-ruby/dist/"

		wrap([$class: 'MaskPasswordsBuildWrapper']) {
			// Feature
			buildPlugin('Feature Build') {
				dependencies = [
					'studio3-core': 'Studio/studio3',
					'studio3-php': 'Studio/studio3-php',
					'studio3-pydev': 'Studio/Pydev',
					'studio3-ruby': 'Studio/studio3-ruby'
				]
				builder = 'com.aptana.studio.build'
				properties = [
					'studio3.p2.repo': studio3Repo,
					'php.p2.repo': phpRepo,
					'pydev.p2.repo': pydevRepo,
					'radrails.p2.repo': rubyRepo,
					'sign.alias': 'appcelerator',
					'sign.keystore': '$KEYSTORE',
					'sign.storepass': '$STOREPASS',
					'sign.keypass': '$STOREPASS'
				]
			}

			stage('Shuffling') {
				// set stream for windows installer later
				if (env.BRANCH_NAME.equals('master')) {
					stream = 'rc'
				} else if (env.BRANCH_NAME.equals('release')) {
					stream = 'beta'
				}
				// Clean everything but dist dir
				sh 'git clean -fdx -e dist/'
				// Copy the artifacts from first step into studio3-feature/dist
				sh 'mkdir studio3-feature'
				sh 'mv dist/ studio3-feature/'
			}
			def studio3FeatureRepo = "file://${env.WORKSPACE}/studio3-feature/dist/"

			// RCP
			buildPlugin('RCP Build') {
				dependencies = [:]
				builder = 'com.aptana.rcp.build'
				properties = [
					'studio3-feature.p2.repo': studio3FeatureRepo,
					'sign.alias': 'appcelerator',
					'sign.keystore': '$KEYSTORE',
					'sign.storepass': '$STOREPASS',
					'sign.keypass': '$STOREPASS'
				]
			}

			stash name: 'winZip', includes: 'dist/studio3.win32.win32.x86.zip'
			stash name: 'macZip', includes: 'dist/studio3.macosx.cocoa.x86_64.zip'
			stash name: 'macBuilder', includes: 'builders/com.aptana.mac.installer/**/*'
		} // end mask passwords wrapper
	} catch (e) {
		// if any exception occurs, mark the build as failed
		currentBuild.result = 'FAILURE'
		//office365ConnectorSend(message: 'Build failed', status: currentBuild.result, webhookUrl: 'https://outlook.office.com/webhook/ba1960f7-fcca-4b2c-a5f3-095ff9c87b22@300f59df-78e6-436f-9b27-b64973e34f7d/JenkinsCI/5dcba6d96f54460d9264e690b26b663e/72931ee3-e99d-4daf-84d2-1427168af2d9')
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
						bat "ant -Dwin.source.url=file://${env.WORKSPACE}/dist/studio3.win32.win32.x86.zip -f builders/com.aptana.win.installer/build.xml unpack-archives"

						// FIXME I don't think we sign the installer!
						// 'password': '$STOREPASS',
						def propertiesContent = """msi.source.url=http://preview.appcelerator.com/aptana/studio3/standalone/install/${stream}/AptanaStudio.msi
installer.command=C:\\\\Program Files (x86)\\\\Caphyon\\\\Advanced Installer 11.8\\\\bin\\\\x86\\\\AdvancedInstaller.com
"""
						writeFile file: 'override.properties', text: propertiesContent
						bat "ant -propertyfile override.properties -f builders/com.aptana.win.installer/build.xml main"
					} // withEnv
				} // timeout
				archiveArtifacts artifacts: 'dist/*.exe, dist/*.msi, dist/*.cab'
				step([$class: 'WsCleanup', notFailBuild: true])
			}
		},
		'Mac Installer': {
			node('osx && packages && ant && certs') {
				unstash 'macZip'
				unstash 'macBuilder'

				timeout(10) {
					withEnv(["PATH+ANT=${tool name: 'Ant 1.9.2', type: 'ant'}/bin"]) {
						sh "ant -Dmac.source.url=file://${env.WORKSPACE}/dist/studio3.macosx.cocoa.x86_64.zip -f builders/com.aptana.mac.installer/build.xml"
					}
				}

				// TODO Check for textFinder('code object is not signed at all', '', true)
				archiveArtifacts artifacts: 'builders/com.aptana.mac.installer/staging/*.dmg'
				step([$class: 'WsCleanup', notFailBuild: true])
			}
		}
	)
}
