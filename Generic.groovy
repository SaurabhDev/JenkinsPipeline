#!/usr/bin/groovy
/**
  * Function: Read configurations from Jenkins file and implements the DSL elements and hence you can extend the pipeline.
  * Author  : Saurabh Singhal 
  * Date    : 27 Sep 2017
  * Params  : 
  **/
  
import main.com.rocc.stages.impl.*
String WORKSPACE = pwd()

def call(body) {
  def config = [:]
  body.resolveStrategy = Closure.DELEGATE_FIRST
  body.delegate = config
  body()  
  try {
    timestamps {
      stage('\u2776 Initialize') {
        try {
          def wspace     = "${WORKSPACE}"
          def MAVEN_HOME = "${config.MAVEN_HOME}"
          def ANT_HOME   = "${config.ANT_HOME}"
          env.MAVEN_HOME = "${MAVEN_HOME}"
          env.ANT_HOME   = "${ANT_HOME}"
          env.wspace     = "${wspace}"
          env.PATH       = "${MAVEN_HOME}/bin:${env.ANT_HOME}:${env.PATH}"
		  def buildNumber = "${env.BUILD_NUMBER}"
          echo "\u001B[35m MAVEN_HOME => ${env.MAVEN_HOME}"
          echo "\u001B[35m WSPACE     => ${wspace}"
          echo "\u001B[35m PATH       => ${env.ANT_HOME}"
		  echo "\u001B[35m BUILD_NUMBER =>${buildNumber}"
		}
        catch (Exception groovyEx) {
          echo groovyEx.getMessage()
          echo groovyEx.getCause()
          throw groovyEx
        }
      }
      stage('\u2777 GetLatest') {
        def co_obj = new GITCheckout()
        co_obj.gitCheckout( "${WORKSPACE}")
        def dl_obj = new ATGAntBuild()
        dl_obj.copy( "${WORKSPACE}")
      }
      stage('\u2778 Compile Code') {
       println "Compile code skipped"  
      }
      stage('\u2779 UnitTesting') {
          println "Unit Testing skipped"  
      }
      stage('\u277A Sonar Analysis') {
       
          println "Sonar Analysis skipped"  
        
      }
     
      stage('\u277C Deployment') {
         println "Deployment Skipped"
      }
      stage('\u277D Integration Testing') {
		 println "Function Testing Skipped"
      }
      stage('\u277E Zap Security Testing') {
	         println "Security Testing Skipped"
      }
	  stage('\u2776 \u0030 Jmeter Testing') 
	  { 
			println "Please run deployment before Jmeter Testing."			        
      }
    }
  }
  catch (Exception groovyEx) {
    echo groovyEx.getMessage()
    echo groovyEx.getCause()
    throw groovyEx
  }
}