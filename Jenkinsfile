#! groovy
// Keep logs/reports/etc of last 15 builds, only keep build artifacts of last 2 builds
// (Artifacts take roughly ~1.2Gb per build!)
properties([buildDiscarder(logRotator(numToKeepStr: '15', artifactNumToKeepStr: '2'))])

timestamps {
	def stream = 'nightly'
	def gitCommit = ''
	def targetBranch = 'development'
	def isPR = false

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
			isPR = env.BRANCH_NAME.startsWith('PR-')
			if (isPR) {
				targetBranch = env.CHANGE_TARGET
			} else {
				targetBranch = env.BRANCH_NAME
			}
			// set stream for windows installer later
			if (targetBranch.equals('master')) {
				stream = 'rc'
			} else if (targetBranch.equals('release')) {
				stream = 'beta'
			}
			stash name: 'winBuilder', includes: 'builders/com.aptana.win.installer/**/*'
			stash name: 'macBuilder', includes: 'builders/com.aptana.mac.installer/**/*'
		}

		def studio3Repo = "file://${pwd()}/studio3/dist/"
		def studio3TestsRepo = "file://${pwd()}/studio3/dist-tests/"
		def phpRepo = "file://${pwd()}/studio3-php/dist/"
		def pydevRepo = "file://${pwd()}/Pydev/dist/"
		def rubyRepo = "file://${pwd()}/studio3-ruby/dist/"

		stage('Dependencies') {
			step([$class: 'CopyArtifact',
				filter: 'dist/,dist-tests/',
				fingerprintArtifacts: true,
				selector: lastSuccessful(),
				projectName: "/aptana-studio/studio3/${targetBranch}",
				target: 'studio3'])
			step([$class: 'CopyArtifact',
				filter: 'dist/',
				fingerprintArtifacts: true,
				selector: lastSuccessful(),
				projectName: "/aptana-studio/studio3-php/${targetBranch}",
				target: 'studio3-php'])
			step([$class: 'CopyArtifact',
				filter: 'dist/',
				fingerprintArtifacts: true,
				selector: lastSuccessful(),
				projectName: "/aptana-studio/Pydev/${targetBranch}",
				target: 'Pydev'])
			step([$class: 'CopyArtifact',
				filter: 'dist/',
				fingerprintArtifacts: true,
				selector: lastSuccessful(),
				projectName: "/aptana-studio/studio3-ruby/${targetBranch}",
				target: 'studio3-ruby'])
		}

		stage('Build') {
			withEnv(["PATH+MAVEN=${tool name: 'Maven 3.5.0', type: 'maven'}/bin"]) {
				withCredentials([usernamePassword(credentialsId: 'aca99bee-0f1e-4fc5-a3da-3dfd73f66432', passwordVariable: 'STOREPASS', usernameVariable: 'ALIAS')]) {
					wrap([$class: 'Xvnc', takeScreenshot: false, useXauthority: true]) {
						try {
							timeout(30) {
								// TODO Get package vs verify goals running in separate stages!
								sh "mvn -Dstudio3.p2.repo.url=${studio3Repo} -Dstudio3.tests.p2.repo.url=${studio3TestsRepo} -Dphp.p2.repo.url=${phpRepo} -Dpython.p2.repo.url=${pydevRepo} -Druby.p2.repo.url=${rubyRepo} -Dmaven.test.failure.ignore=true -Djarsigner.keypass=${env.STOREPASS} -Djarsigner.storepass=${env.STOREPASS} -Djarsigner.keystore=${env.KEYSTORE} clean verify"
							}
						} finally {
							// record tests even if we failed
							junit 'tests/*/target/surefire-reports/TEST-*.xml'
						}
					} // xvnc
				} // withCredentials
			} // withEnv(maven)
			// Archive the generated p2 repo
			dir('releng/com.aptana.studio.ide.update/target') {
				// To keep backwards compatability with existing build pipeline, rename to "dist"
				sh 'mv repository dist'
				archiveArtifacts artifacts: 'dist/**/*'
				def jarName = sh(returnStdout: true, script: 'ls dist/features/com.aptana.feature.studio_*.jar').trim()
				def version = (jarName =~ /.*?_(.+)\.jar/)[0][1]
				currentBuild.displayName = "#${version}-${currentBuild.number}"
			}
			// Archive the generated p2 repo and zipfiles for the RCP
			dir('releng/com.aptana.studio.ide.product/target') {
				// retain backwards compat: make the dir name rcp
				sh 'mv repository rcp'
				sh 'cp products/*.zip rcp/'
				// fix permissions so group/world readable
				sh 'chmod a+r -R rcp'
				sh 'chmod g+r -R rcp'
				archiveArtifacts artifacts: 'rcp/**/*'
			}
		} // stage('Build')
	} // end node

	stage('Installers') {
		parallel(
			'Windows Installer': {
				node('windows && advanced_installer && ant') {
					unarchive mapping: ['rcp/aptana.studio-win32.win32.x86_64.zip': 'aptana.studio-win32.win32.x86_64.zip']
					unstash 'winBuilder'
					if (fileExists('win')) {
						bat 'rmdir win /Q /S' // remove win dir if it exists already
					}

					timeout(20) {
						withEnv(["PATH+ANT=${tool name: 'Ant 1.9.2', type: 'ant'}\\bin"]) {
							bat "ant -Dwin.source.zip=aptana.studio-win32.win32.x86_64.zip -Dwin.source.url=file:///${env.WORKSPACE}/aptana.studio-win32.win32.x86_64.zip -f builders/com.aptana.win.installer/build.xml unpack-archives"

							withCredentials([usernamePassword(credentialsId: 'aca99bee-0f1e-4fc5-a3da-3dfd73f66432', passwordVariable: 'STOREPASS', usernameVariable: 'ALIAS')]) {
								propertiesContent = """msi.source.url=http://preview.appcelerator.com/aptana/studio3/standalone/install/${stream}/AptanaStudio.msi
installer.command=C:\\\\Program Files (x86)\\\\Caphyon\\\\Advanced Installer 11.8\\\\bin\\\\x86\\\\AdvancedInstaller.com
password=${env.STOREPASS}
"""
								writeFile file: 'override.properties', text: propertiesContent
							}
							bat 'ant -propertyfile override.properties -f builders/com.aptana.win.installer/build.xml main'
						} // withEnv
						bat 'rename dist win'
					} // timeout
					archiveArtifacts artifacts: 'win/*.exe, win/*.msi, win/*.cab'
					step([$class: 'WsCleanup', notFailBuild: true])
				}
			},
			'Mac Installer': {
				node('osx && packages && ant && certs') {
					unarchive mapping: ['rcp/aptana.studio-macosx.cocoa.x86_64.zip': 'aptana.studio-macosx.cocoa.x86_64.zip']
					unstash 'macBuilder'
					sh 'rm -rf mac' // remove mac dir if it exists already

					timeout(10) {
						withEnv(["PATH+ANT=${tool name: 'Ant 1.9.2', type: 'ant'}/bin"]) {
							sh "ant -Dmac.source.url=file://${env.WORKSPACE}/aptana.studio-macosx.cocoa.x86_64.zip -f builders/com.aptana.mac.installer/build.xml"
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
			},
			failFast: true
		) // parallel
	} // 'Installers' stage
}
