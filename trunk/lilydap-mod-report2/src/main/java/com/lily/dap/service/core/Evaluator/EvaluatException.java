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


/**
 *
 * Represents any of the exception conditions that arise during the
 * operation evaluation of the evaluator.
 **/

public class EvaluatException
  extends Exception
{
  //-------------------------------------
  // Member variables
  //-------------------------------------

  Throwable mRootCause;

  //-------------------------------------
  /**
   *
   * Constructor
   **/
  public EvaluatException ()
  {
    super ();
  }

  //-------------------------------------
  /**
   *
   * Constructor
   **/
  public EvaluatException (String pMessage)
  {
    super (pMessage);
  }

  //-------------------------------------
  /**
   *
   * Constructor
   **/
  public EvaluatException (Throwable pRootCause)
  {
    mRootCause = pRootCause;
  }

  //-------------------------------------
  /**
   *
   * Constructor
   **/
  public EvaluatException (String pMessage,
		      Throwable pRootCause)
  {
    super (pMessage);
    mRootCause = pRootCause;
  }

  //-------------------------------------
  /**
   *
   * Returns the root cause
   **/
  public Throwable getRootCause ()
  {
    return mRootCause;
  }

  //-------------------------------------
  /**
   *
   * String representation
   **/
  public String toString ()
  {
    if (getMessage () == null) {
      return mRootCause.toString ();
    }
    else if (mRootCause == null) {
      return getMessage ();
    }
    else {
      return getMessage () + ": " + mRootCause;
    }
  }

  //-------------------------------------
}
