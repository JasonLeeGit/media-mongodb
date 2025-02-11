package com.coders.software.media.test.suite;

import org.junit.platform.suite.api.IncludeTags;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.SelectPackages;
import org.junit.platform.suite.api.Suite;
import org.junit.platform.suite.api.SuiteDisplayName;

import com.coders.software.media.controller.DeleteMediaControllerTest;
import com.coders.software.media.controller.FindMediaControllerRepoTest;
import com.coders.software.media.controller.SaveMediaControllerTest;
import com.coders.software.media.controller.UpdateMediaControllerTest;
import com.coders.software.media.helper.ValidationHelperTest;

@SelectPackages({ 
	"com.coders.software.media.controller", 
	"com.coders.software.media.helper"
})

@IncludeTags("test")
@Suite
@SuiteDisplayName("Media Mongdb Test Suite")
@SelectClasses({ 
	ValidationHelperTest.class, 
	DeleteMediaControllerTest.class, 
	FindMediaControllerRepoTest.class, 
	SaveMediaControllerTest.class,
	UpdateMediaControllerTest.class
})
public class JunitTestSuite {
	// TODO fix me Junit 5 configuration
	// https://stackoverflow.com/questions/76501728/java-lang-illegalstateexception-failed-to-load-applicationcontext-for-junit-5-t
}