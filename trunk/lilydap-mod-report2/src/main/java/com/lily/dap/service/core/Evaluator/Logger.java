/*
 * Copyright 1999-2004 The Apache Software Foundation.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */ 

package com.lily.dap.service.core.Evaluator;

import java.io.PrintStream;
import java.text.MessageFormat;

/**
 *
 * <p>The evaluator may pass an instance of this class to operators
 * and expressions during evaluation.  They should use this to log any
 * warning or error messages that might come up.  This allows all of
 * our logging policies to be concentrated in one class.
 *
 * <p>Errors are conditions that are severe enough to abort operation.
 * Warnings are conditions through which the operation may continue,
 * but which should be reported to the developer.
 **/

public class Logger
{
  //-------------------------------------
  // Member variables
  //-------------------------------------

  PrintStream mOut;

  //-------------------------------------
  /**
   *
   * Constructor
   *
   * @param pOut the PrintStream to which warnings should be printed
   **/
  public Logger (PrintStream pOut)
  {
    mOut = pOut;
  }

  //-------------------------------------
  /**
   *
   * Returns true if the application should even bother to try logging
   * a warning.
   **/
  public boolean isLoggingWarning ()
  {
    return false;
  }

  //-------------------------------------
  /**
   *
   * Logs a warning
   **/
  public void logWarning (String pMessage,
			  Throwable pRootCause)
    throws EvaluatException
  {
    if (isLoggingWarning ()) {
      if (pMessage == null) {
	System.out.println (pRootCause);
      }
      else if (pRootCause == null) {
	System.out.println (pMessage);
      }
      else {
	System.out.println (pMessage + ": " + pRootCause);
      }
    }
  }

  //-------------------------------------
  /**
   *
   * Logs a warning
   **/
  public void logWarning (String pTemplate)
    throws EvaluatException
  {
    if (isLoggingWarning ()) {
      logWarning (pTemplate, null);
    }
  }

  //-------------------------------------
  /**
   *
   * Logs a warning
   **/
  public void logWarning (Throwable pRootCause)
    throws EvaluatException
  {
    if (isLoggingWarning ()) {
      logWarning (null, pRootCause);
    }
  }

  //-------------------------------------
  /**
   *
   * Logs a warning
   **/
  public void logWarning (String pTemplate,
			  Object pArg0)
    throws EvaluatException
  {
    if (isLoggingWarning ()) {
      logWarning
	(MessageFormat.format
	 (pTemplate,
	  new Object [] {
	    "" + pArg0,
	  }));
    }
  }

  //-------------------------------------
  /**
   *
   * Logs a warning
   **/
  public void logWarning (String pTemplate,
			  Throwable pRootCause,
			  Object pArg0)
    throws EvaluatException
  {
    if (isLoggingWarning ()) {
      logWarning
	(MessageFormat.format
	 (pTemplate,
	  new Object [] {
	    "" + pArg0,
	  }),
	 pRootCause);
    }
  }

  //-------------------------------------
  /**
   *
   * Logs a warning
   **/
  public void logWarning (String pTemplate,
			  Object pArg0,
			  Object pArg1)
    throws EvaluatException
  {
    if (isLoggingWarning ()) {
      logWarning
	(MessageFormat.format
	 (pTemplate,
	  new Object [] {
	    "" + pArg0,
	    "" + pArg1,
	  }));
    }
  }

  //-------------------------------------
  /**
   *
   * Logs a warning
   **/
  public void logWarning (String pTemplate,
			  Throwable pRootCause,
			  Object pArg0,
			  Object pArg1)
    throws EvaluatException
  {
    if (isLoggingWarning ()) {
      logWarning
	(MessageFormat.format
	 (pTemplate,
	  new Object [] {
	    "" + pArg0,
	    "" + pArg1,
	  }),
	 pRootCause);
    }
  }

  //-------------------------------------
  /**
   *
   * Logs a warning
   **/
  public void logWarning (String pTemplate,
			  Object pArg0,
			  Object pArg1,
			  Object pArg2)
    throws EvaluatException
  {
    if (isLoggingWarning ()) {
      logWarning
	(MessageFormat.format
	 (pTemplate,
	  new Object [] {
	    "" + pArg0,
	    "" + pArg1,
	    "" + pArg2,
	  }));
    }
  }

  //-------------------------------------
  /**
   *
   * Logs a warning
   **/
  public void logWarning (String pTemplate,
			  Throwable pRootCause,
			  Object pArg0,
			  Object pArg1,
			  Object pArg2)
    throws EvaluatException
  {
    if (isLoggingWarning ()) {
      logWarning
	(MessageFormat.format
	 (pTemplate,
	  new Object [] {
	    "" + pArg0,
	    "" + pArg1,
	    "" + pArg2,
	  }),
	 pRootCause);
    }
  }

  //-------------------------------------
  /**
   *
   * Logs a warning
   **/
  public void logWarning (String pTemplate,
			  Object pArg0,
			  Object pArg1,
			  Object pArg2,
			  Object pArg3)
    throws EvaluatException
  {
    if (isLoggingWarning ()) {
      logWarning
	(MessageFormat.format
	 (pTemplate,
	  new Object [] {
	    "" + pArg0,
	    "" + pArg1,
	    "" + pArg2,
	    "" + pArg3,
	  }));
    }
  }

  //-------------------------------------
  /**
   *
   * Logs a warning
   **/
  public void logWarning (String pTemplate,
			  Throwable pRootCause,
			  Object pArg0,
			  Object pArg1,
			  Object pArg2,
			  Object pArg3)
    throws EvaluatException
  {
    if (isLoggingWarning ()) {
      logWarning
	(MessageFormat.format
	 (pTemplate,
	  new Object [] {
	    "" + pArg0,
	    "" + pArg1,
	    "" + pArg2,
	    "" + pArg3,
	  }),
	 pRootCause);
    }
  }

  //-------------------------------------
  /**
   *
   * Logs a warning
   **/
  public void logWarning (String pTemplate,
			  Object pArg0,
			  Object pArg1,
			  Object pArg2,
			  Object pArg3,
			  Object pArg4)
    throws EvaluatException
  {
    if (isLoggingWarning ()) {
      logWarning
	(MessageFormat.format
	 (pTemplate,
	  new Object [] {
	    "" + pArg0,
	    "" + pArg1,
	    "" + pArg2,
	    "" + pArg3,
	    "" + pArg4,
	  }));
    }
  }

  //-------------------------------------
  /**
   *
   * Logs a warning
   **/
  public void logWarning (String pTemplate,
			  Throwable pRootCause,
			  Object pArg0,
			  Object pArg1,
			  Object pArg2,
			  Object pArg3,
			  Object pArg4)
    throws EvaluatException
  {
    if (isLoggingWarning ()) {
      logWarning
	(MessageFormat.format
	 (pTemplate,
	  new Object [] {
	    "" + pArg0,
	    "" + pArg1,
	    "" + pArg2,
	    "" + pArg3,
	    "" + pArg4,
	  }),
	 pRootCause);
    }
  }

  //-------------------------------------
  /**
   *
   * Logs a warning
   **/
  public void logWarning (String pTemplate,
			  Object pArg0,
			  Object pArg1,
			  Object pArg2,
			  Object pArg3,
			  Object pArg4,
			  Object pArg5)
    throws EvaluatException
  {
    if (isLoggingWarning ()) {
      logWarning
	(MessageFormat.format
	 (pTemplate,
	  new Object [] {
	    "" + pArg0,
	    "" + pArg1,
	    "" + pArg2,
	    "" + pArg3,
	    "" + pArg4,
	    "" + pArg5,
	  }));
    }
  }

  //-------------------------------------
  /**
   *
   * Logs a warning
   **/
  public void logWarning (String pTemplate,
			  Throwable pRootCause,
			  Object pArg0,
			  Object pArg1,
			  Object pArg2,
			  Object pArg3,
			  Object pArg4,
			  Object pArg5)
    throws EvaluatException
  {
    if (isLoggingWarning ()) {
      logWarning
	(MessageFormat.format
	 (pTemplate,
	  new Object [] {
	    "" + pArg0,
	    "" + pArg1,
	    "" + pArg2,
	    "" + pArg3,
	    "" + pArg4,
	    "" + pArg5,
	  }),
	 pRootCause);
    }
  }

  //-------------------------------------
  /**
   *
   * Returns true if the application should even bother to try logging
   * an error.
   **/
  public boolean isLoggingError ()
  {
    return true;
  }

  //-------------------------------------
  /**
   *
   * Logs an error
   **/
  public void logError (String pMessage,
			Throwable pRootCause)
    throws EvaluatException
  {
    if (isLoggingError ()) {
      if (pMessage == null) {
	throw new EvaluatException (pRootCause);
      }
      else if (pRootCause == null) {
	throw new EvaluatException (pMessage);
      }
      else {
	throw new EvaluatException (pMessage, pRootCause);
      }
    }
  }

  //-------------------------------------
  /**
   *
   * Logs an error
   **/
  public void logError (String pTemplate)
    throws EvaluatException
  {
    if (isLoggingError ()) {
      logError (pTemplate, null);
    }
  }

  //-------------------------------------
  /**
   *
   * Logs an error
   **/
  public void logError (Throwable pRootCause)
    throws EvaluatException
  {
    if (isLoggingError ()) {
      logError (null, pRootCause);
    }
  }

  //-------------------------------------
  /**
   *
   * Logs an error
   **/
  public void logError (String pTemplate,
			Object pArg0)
    throws EvaluatException
  {
    if (isLoggingError ()) {
      logError
	(MessageFormat.format
	 (pTemplate,
	  new Object [] {
	    "" + pArg0,
	  }));
    }
  }

  //-------------------------------------
  /**
   *
   * Logs an error
   **/
  public void logError (String pTemplate,
			Throwable pRootCause,
			Object pArg0)
    throws EvaluatException
  {
    if (isLoggingError ()) {
      logError
	(MessageFormat.format
	 (pTemplate,
	  new Object [] {
	    "" + pArg0,
	  }),
	 pRootCause);
    }
  }

  //-------------------------------------
  /**
   *
   * Logs an error
   **/
  public void logError (String pTemplate,
			Object pArg0,
			Object pArg1)
    throws EvaluatException
  {
    if (isLoggingError ()) {
      logError
	(MessageFormat.format
	 (pTemplate,
	  new Object [] {
	    "" + pArg0,
	    "" + pArg1,
	  }));
    }
  }

  //-------------------------------------
  /**
   *
   * Logs an error
   **/
  public void logError (String pTemplate,
			Throwable pRootCause,
			Object pArg0,
			Object pArg1)
    throws EvaluatException
  {
    if (isLoggingError ()) {
      logError
	(MessageFormat.format
	 (pTemplate,
	  new Object [] {
	    "" + pArg0,
	    "" + pArg1,
	  }),
	 pRootCause);
    }
  }

  //-------------------------------------
  /**
   *
   * Logs an error
   **/
  public void logError (String pTemplate,
			Object pArg0,
			Object pArg1,
			Object pArg2)
    throws EvaluatException
  {
    if (isLoggingError ()) {
      logError
	(MessageFormat.format
	 (pTemplate,
	  new Object [] {
	    "" + pArg0,
	    "" + pArg1,
	    "" + pArg2,
	  }));
    }
  }

  //-------------------------------------
  /**
   *
   * Logs an error
   **/
  public void logError (String pTemplate,
			Throwable pRootCause,
			Object pArg0,
			Object pArg1,
			Object pArg2)
    throws EvaluatException
  {
    if (isLoggingError ()) {
      logError
	(MessageFormat.format
	 (pTemplate,
	  new Object [] {
	    "" + pArg0,
	    "" + pArg1,
	    "" + pArg2,
	  }),
	 pRootCause);
    }
  }

  //-------------------------------------
  /**
   *
   * Logs an error
   **/
  public void logError (String pTemplate,
			Object pArg0,
			Object pArg1,
			Object pArg2,
			Object pArg3)
    throws EvaluatException
  {
    if (isLoggingError ()) {
      logError
	(MessageFormat.format
	 (pTemplate,
	  new Object [] {
	    "" + pArg0,
	    "" + pArg1,
	    "" + pArg2,
	    "" + pArg3,
	  }));
    }
  }

  //-------------------------------------
  /**
   *
   * Logs an error
   **/
  public void logError (String pTemplate,
			Throwable pRootCause,
			Object pArg0,
			Object pArg1,
			Object pArg2,
			Object pArg3)
    throws EvaluatException
  {
    if (isLoggingError ()) {
      logError
	(MessageFormat.format
	 (pTemplate,
	  new Object [] {
	    "" + pArg0,
	    "" + pArg1,
	    "" + pArg2,
	    "" + pArg3,
	  }),
	 pRootCause);
    }
  }

  //-------------------------------------
  /**
   *
   * Logs an error
   **/
  public void logError (String pTemplate,
			Object pArg0,
			Object pArg1,
			Object pArg2,
			Object pArg3,
			Object pArg4)
    throws EvaluatException
  {
    if (isLoggingError ()) {
      logError
	(MessageFormat.format
	 (pTemplate,
	  new Object [] {
	    "" + pArg0,
	    "" + pArg1,
	    "" + pArg2,
	    "" + pArg3,
	    "" + pArg4,
	  }));
    }
  }

  //-------------------------------------
  /**
   *
   * Logs an error
   **/
  public void logError (String pTemplate,
			Throwable pRootCause,
			Object pArg0,
			Object pArg1,
			Object pArg2,
			Object pArg3,
			Object pArg4)
    throws EvaluatException
  {
    if (isLoggingError ()) {
      logError
	(MessageFormat.format
	 (pTemplate,
	  new Object [] {
	    "" + pArg0,
	    "" + pArg1,
	    "" + pArg2,
	    "" + pArg3,
	    "" + pArg4,
	  }),
	 pRootCause);
    }
  }

  //-------------------------------------
  /**
   *
   * Logs an error
   **/
  public void logError (String pTemplate,
			Object pArg0,
			Object pArg1,
			Object pArg2,
			Object pArg3,
			Object pArg4,
			Object pArg5)
    throws EvaluatException
  {
    if (isLoggingError ()) {
      logError
	(MessageFormat.format
	 (pTemplate,
	  new Object [] {
	    "" + pArg0,
	    "" + pArg1,
	    "" + pArg2,
	    "" + pArg3,
	    "" + pArg4,
	    "" + pArg5,
	  }));
    }
  }

  //-------------------------------------
  /**
   *
   * Logs an error
   **/
  public void logError (String pTemplate,
			Throwable pRootCause,
			Object pArg0,
			Object pArg1,
			Object pArg2,
			Object pArg3,
			Object pArg4,
			Object pArg5)
    throws EvaluatException
  {
    if (isLoggingError ()) {
      logError
	(MessageFormat.format
	 (pTemplate,
	  new Object [] {
	    "" + pArg0,
	    "" + pArg1,
	    "" + pArg2,
	    "" + pArg3,
	    "" + pArg4,
	    "" + pArg5,
	  }),
	 pRootCause);
    }
  }

  //-------------------------------------
}
