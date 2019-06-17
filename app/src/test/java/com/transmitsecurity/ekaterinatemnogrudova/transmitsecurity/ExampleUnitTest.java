package com.transmitsecurity.ekaterinatemnogrudova.transmitsecurity;

import android.content.Context;

import com.transmitsecurity.ekaterinatemnogrudova.transmitsecurity.authenticatorsList.AuthenticatorsListContract;
import com.transmitsecurity.ekaterinatemnogrudova.transmitsecurity.authenticatorsList.AuthenticatorsListPresenter;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.stubbing.Answer;
import org.mockito.stubbing.OngoingStubbing;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @RunWith(AndroidJUnit4.class)
    public class ExampleInstrumentedTest {
        @Test
        public void useAppContext() {
            // Context of the app under test.
            Context appContext = InstrumentationRegistry.getTargetContext();

            assertEquals("com.shutterfly.ekaterinatemnogrudova.shutterfly", appContext.getPackageName());
        }
    }

}