#! groovy
@Library('pipeline-build') _

timestamps() {
	def stream = 'nightly'
	def gitCommit = ''
	node('keystore && linux && ant && eclipse && jdk') {
		stage('Checkout') {
			// checkout scm
			// Hack for JENKINS-37658 - see https://support.cloudbees.com/hc/en-us/articles/226122247-How-to-Customize-Checkout-for-Pipeline-Multibranch
			checkout([
				$class: 'GitSCM',
				branches: scm.branches,
				extensions: scm.extensions + [[$class: 'CleanCheckout'], [$class: 'CloneOption', honorRefspec: true, noTags: true, reference: '', shallow: true, depth: 30, timeout: 30]],
				userRemoteConfigs: scm.userRemoteConfigs
			])
			gitCommit = sh(returnStdout: true, script: 'git rev-parse HEAD').trim()
			// set stream for windows installer later
			if (env.BRANCH_NAME.equals('master')) {
				stream = 'rc'
			} else if (env.BRANCH_NAME.equals('release')) {
				stream = 'beta'
			}
			stash name: 'winBuilder', includes: 'builders/com.aptana.win.installer/**/*'
			stash name: 'macBuilder', includes: 'builders/com.aptana.mac.installer/**/*'
		}

		def studio3Repo = "file://${env.WORKSPACE}/studio3-core/dist/"
		def phpRepo = "file://${env.WORKSPACE}/studio3-php/dist/"
		def pydevRepo = "file://${env.WORKSPACE}/studio3-pydev/dist/"
		def rubyRepo = "file://${env.WORKSPACE}/studio3-ruby/dist/"

		// Feature
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
			// Clean everything but dist dir
			sh 'git clean -fdx -e plugin/'
			def before = sh(returnStdout: true, script: 'ls -la').trim()
			echo "Before: ${before}"
			// Force checking out the same rev we started with
			sh "git checkout -f ${gitCommit}"

			// TODO: Wipe the non-rcp plugins/features
			sh 'mv plugins src_plugins'
			sh 'mkdir plugins'
			sh 'mv src_plugins/com.aptana.rcp plugins/com.aptana.rcp'
			sh 'mv src_plugins/com.aptana.rcp.studio plugins/com.aptana.rcp.studio'

			sh 'mv features src_features'
			sh 'mkdir features'
			sh 'mv src_features/com.aptana.feature.rcp features/com.aptana.feature.rcp'
			def after = sh(returnStdout: true, script: 'ls -la').trim()
			echo "After: ${after}"
		}
		def studio3FeatureRepo = "file://${env.WORKSPACE}/plugin/"

		// RCP
		buildPlugin('RCP Build') {
			dependencies = [:]
			builder = 'com.aptana.rcp.build'
			outputDir = 'rcp'
			properties = ['studio3-feature.p2.repo': studio3FeatureRepo]
		}

		stage('Archive Zips') {
			// Archive the os/arch zips
			// Don't include the zipped p2 repo
			archiveArtifacts artifacts: "rcp/*.zip", excludes: "rcp/com.aptana.rcp.product-*.zip"
			step([$class: 'WsCleanup', notFailBuild: true])
		}
	} // end node

	stage('Installers') {
		parallel(
			'Windows Installer': {
				node('windows && advanced_installer && ant') {
					unarchive mapping: ['rcp/studio3.win32.win32.x86.zip': 'studio3.win32.win32.x86.zip']
					unstash 'winBuilder'

					timeout(20) {
						withEnv(["PATH+ANT=${tool name: 'Ant 1.9.2', type: 'ant'}\\bin"]) {
							bat "ant -Dwin.source.url=file:///${env.WORKSPACE}/studio3.win32.win32.x86.zip -f builders/com.aptana.win.installer/build.xml unpack-archives"

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
					unarchive mapping: ['studio3.macosx.cocoa.x86_64.zip': 'studio3.macosx.cocoa.x86_64.zip']
					unstash 'macBuilder'

					timeout(10) {
						withEnv(["PATH+ANT=${tool name: 'Ant 1.9.2', type: 'ant'}/bin"]) {
							sh "ant -Dmac.source.url=file://${env.WORKSPACE}/studio3.macosx.cocoa.x86_64.zip -f builders/com.aptana.mac.installer/build.xml"
						}
						// Move DMG to new 'mac' directory so merged artifacts get separated nicely
						if (fileExists('mac')) {
							sh 'rm -rf mac'
						}
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