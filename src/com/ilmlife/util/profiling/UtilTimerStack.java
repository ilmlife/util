///*
// * Copyright (c) 2002-2003, Atlassian Software Systems Pty Ltd All rights reserved.
// *
// * Redistribution and use in source and binary forms, with or without modification,
// * are permitted provided that the following conditions are met:
// * 
// *     * Redistributions of source code must retain the above copyright notice,
// * this list of conditions and the following disclaimer.
// *     * Redistributions in binary form must reproduce the above copyright notice,
// * this list of conditions and the following disclaimer in the documentation and/or
// * other materials provided with the distribution.
// *     * Neither the name of Atlassian Software Systems Pty Ltd nor the names of
// * its contributors may be used to endorse or promote products derived from this
// * software without specific prior written permission.
// * 
// * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
// * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
// * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
// * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR
// * ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
// * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
// * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
// * ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
// * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
// * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
// */
//package com.ilmlife.util.profiling;
//
//import org.apache.log4j.Logger;
//
//import com.ilmlife.util.container.Holder;
//
//
//public class UtilTimerStack
//{
//
//    // A reference to the current ProfilingTimerBean
//    protected static ThreadLocal<ProfilingTimerBean> current = new ThreadLocal<ProfilingTimerBean>();
//
//    private static Logger logger = null;
//    
//    /**
//     * Create and start a performance profiling with the <code>name</code> given. Deal with 
//     * profile hierarchy automatically, so caller don't have to be concern about it.
//     * 
//     * @param name profile name
//     */
//    public static void push(String name)
//    {
//        if (!isActive())
//            return;
//
//        //create a new timer and start it
//        ProfilingTimerBean newTimer = new ProfilingTimerBean(name);
//        newTimer.setStartTime();
//
//        //if there is a current timer - add the new timer as a child of it
//        ProfilingTimerBean currentTimer = (ProfilingTimerBean) current.get();
//        if (currentTimer != null)
//        {
//            currentTimer.addChild(newTimer);
//        }
//
//        //set the new timer to be the current timer
//        current.set(newTimer);
//    }
//
//    /**
//     * End a preformance profiling with the <code>name</code> given. Deal with
//     * profile hierarchy automatically, so caller don't have to be concern about it.
//     * 
//     * @param name profile name
//     */
//    public static void pop(String name)
//    {
//        if (!isActive())
//            return;
//
//        ProfilingTimerBean currentTimer = (ProfilingTimerBean) current.get();
//
//        //if the timers are matched up with each other (ie push("a"); pop("a"));
//        if (currentTimer != null && name != null && name.equals(currentTimer.getResource()))
//        {
//            currentTimer.setEndTime();
//            ProfilingTimerBean parent = currentTimer.getParent();
//            //if we are the root timer, then print out the times
//            if (parent == null)
//            {
//                printTimes(currentTimer);
//                current.set(null); //for those servers that use thread pooling
//            }
//            else
//            {
//                current.set(parent);
//            }
//        }
//        else
//        {
//            //if timers are not matched up, then print what we have, and then print warning.
//            if (currentTimer != null)
//            {
//                printTimes(currentTimer);
//                current.set(null); //prevent printing multiple times
//                logger.warn("Unmatched Timer.  Was expecting " + currentTimer.getResource() + ", instead got " + name);
//            }
//        }
//
//
//    }
//
//    /**
//     * Do a log (at INFO level) of the time taken for this particular profiling.
//     * 
//     * @param currentTimer profiling timer bean
//     */
//    private static void printTimes(ProfilingTimerBean currentTimer)
//    {
//    	Holder<Boolean> isSlow = new Holder<Boolean>();
//    	isSlow.setValue(false);
//    	String msg = currentTimer.getPrintable("",isSlow);
//    	if ( isSlow.getValue() == true ) {
//    		logger.info( msg );
//    	}
//    	else {
//    		logger.debug( msg );
//    	}
//    }
//
//    /**
//     * Determine if profiling is being activated, by searching for a system property
//     * 'xwork.profile.activate', default to false (profiling is off).
//     * 
//     * @return <tt>true</tt>, if active, <tt>false</tt> otherwise.
//     */
//    public static boolean isActive()
//    {
//        return ProfilerConf.getInstance().isProfileActivate();
//    }
//
//    /**
//     * Turn profiling on or off.
//     * 
//     * @param active
//     */
////    public static void setActive(boolean active)
////    {
////        if (active)
////            System.setProperty(ACTIVATE_PROPERTY, "true");
////        else
////        	System.clearProperty(ACTIVATE_PROPERTY);
////    }
//
//
//    /**
//     * A convenience method that allows <code>block</code> of code subjected to profiling to be executed 
//     * and avoid the need of coding boiler code that does pushing (UtilTimeBean.push(...)) and 
//     * poping (UtilTimerBean.pop(...)) in a try ... finally ... block.
//     * 
//     * <p/>
//     * 
//     * Example of usage:
//     * <pre>
//     * 	 // we need a returning result
//     *   String result = UtilTimerStack.profile("purchaseItem: ", 
//     *       new UtilTimerStack.ProfilingBlock<String>() {
//     *            public String doProfiling() {
//     *               getMyService().purchaseItem(....)
//     *               return "Ok";
//     *            }
//     *       });
//     * </pre>
//     * or
//     * <pre>
//     *   // we don't need a returning result
//     *   UtilTimerStack.profile("purchaseItem: ", 
//     *       new UtilTimerStack.ProfilingBlock<String>() {
//     *            public String doProfiling() {
//     *               getMyService().purchaseItem(....)
//     *               return null;
//     *            }
//     *       });
//     * </pre>
//     * 
//     * @param <T> any return value if there's one.
//     * @param name profile name
//     * @param block code block subjected to profiling
//     * @return T
//     * @throws Exception
//     */
//    public static <T> T profile(String name, ProfilingBlock<T> block) throws Exception {
//    	UtilTimerStack.push(name);
//    	try {
//    		return block.doProfiling();
//    	}
//    	finally {
//    		UtilTimerStack.pop(name);
//    	}
//    }
//    
//    /**
//     * A callback interface where code subjected to profile is to be executed. This eliminates the need
//     * of coding boiler code that does pushing (UtilTimerBean.push(...)) and poping (UtilTimerBean.pop(...))
//     * in a try ... finally ... block.
//     * 
//     * @version $Date$ $Id$
//     * 
//     * @param <T>
//     */
//    public static interface ProfilingBlock<T> {
//    	
//    	/**
//    	 * Method that execute the code subjected to profiling.
//    	 * 
//    	 * @return  profiles Type
//    	 * @throws Exception
//    	 */
//    	T doProfiling() throws Exception;
//    }
//}
