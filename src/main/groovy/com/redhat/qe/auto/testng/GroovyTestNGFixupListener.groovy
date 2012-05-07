package com.redhat.qe.auto.testng

import org.testng.IMethodInstance
import org.testng.IMethodInterceptor
import org.testng.ITestContext

// This TestNG listener will filter out the additional "synthetic" methods that Groovy adds 
// to a metaclass, that TestNG picks up when you put a @Test annotation at the class level
//
// TODO: Add support for additional filter strings
//
class GroovyTestNGFixupListener implements IMethodInterceptor {
	List<IMethodInstance> intercept(List<IMethodInstance> methods, ITestContext context) {
		methods.findAll { !( it.method.methodName ==~ /^(super|(set|get)(MetaClass|Property)|this|__).*/ ) }
	}
}
