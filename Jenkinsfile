#! groovy
library 'pipeline-build'
// Keep logs/reports/etc of last 15 builds, only keep build artifacts of last 2 builds
// (Artifacts take roughly ~1.2Gb per build!)
properties([buildDiscarder(logRotator(numToKeepStr: '15', artifactNumToKeepStr: '2'))])

timestamps {
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

		def studio3Repo = "file://${pwd()}/studio3/dist/"
		def phpRepo = "file://${pwd()}/studio3-php/dist/"
		def pydevRepo = "file://${pwd()}/Pydev/dist/"
		def rubyRepo = "file://${pwd()}/studio3-ruby/dist/"

		tage('Dependencies') {
			step([$class: 'CopyArtifact',
				filter: 'dist/',
				fingerprintArtifacts: true,
				selector: lastSuccessful(),
				projectName: "/aptana-studio/studio3/tycho",
				target: 'studio3'])
			step([$class: 'CopyArtifact',
				filter: 'dist/',
				fingerprintArtifacts: true,
				selector: lastSuccessful(),
				projectName: "/aptana-studio/studio3-php/tycho",
				target: 'studio3-php'])
			step([$class: 'CopyArtifact',
				filter: 'dist/',
				fingerprintArtifacts: true,
				selector: lastSuccessful(),
				projectName: "/aptana-studio/Pydev/tycho",
				target: 'Pydev'])
			step([$class: 'CopyArtifact',
				filter: 'dist/',
				fingerprintArtifacts: true,
				selector: lastSuccessful(),
				projectName: "/aptana-studio/studio3-ruby/tycho",
				target: 'studio3-ruby'])
		}

		stage('Build') {
			withEnv(["PATH+MAVEN=${tool name: 'Maven 3.5.0', type: 'maven'}/bin"]) {
				withCredentials([usernamePassword(credentialsId: 'aca99bee-0f1e-4fc5-a3da-3dfd73f66432', passwordVariable: 'STOREPASS', usernameVariable: 'ALIAS')]) {
					wrap([$class: 'Xvnc', takeScreenshot: false, useXauthority: true]) {
						try {
							timeout(30) {
								// TODO Get package vs verify goals running in separate stages!
								sh "mvn -Dstudio3.p2.repo.url=${studio3Repo} -Dphp.p2.repo.url=${phpRepo} -Dpython.p2.repo.url=${pydevRepo} -ruby.p2.repo.url=${rubyRepo} -Dmaven.test.failure.ignore=true -Djarsigner.keypass=${env.STOREPASS} -Djarsigner.storepass=${env.STOREPASS} -Djarsigner.keystore=${env.KEYSTORE} clean verify"
							}
						} finally {
							// record tests even if we failed
							junit 'tests/*/target/surefire-reports/TEST-*.xml'
						}
					} // xvnc
				} // withCredentials
			} // withEnv(maven)
			// Archive the generated p2 repo
			dir('releng/org.python.pydev.update/target') {
				// To keep backwards compatability with existing build pipeline, rename to "dist"
				sh 'mv repository dist'
				archiveArtifacts artifacts: 'dist/**/*'
				def jarName = sh(returnStdout: true, script: 'ls dist/features/com.aptana.pydev.feature_*.jar').trim()
				def version = (jarName =~ /.*?_(.+)\.jar/)[0][1]
				currentBuild.displayName = "#${version}-${currentBuild.number}"
			}
		} // stage('Build')

		stage('Archive Zips') {
			// Archive the os/arch zips
			// Don't include the zipped p2 repo
			archiveArtifacts artifacts: 'rcp/*.zip', excludes: 'rcp/com.aptana.rcp.product-*.zip'
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
				} // node
			}, // windows installer
			'Mac Installer': {
				node('osx && packages && ant && certs') {
					unarchive mapping: ['rcp/studio3.macosx.cocoa.x86_64.zip': 'studio3.macosx.cocoa.x86_64.zip']
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
				} // node
			}, // mac installer
			failFast: true
		)
	}
}
