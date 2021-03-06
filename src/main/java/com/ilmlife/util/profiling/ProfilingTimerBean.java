/*
 * Copyright (c) 2002-2003, Atlassian Software Systems Pty Ltd All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted provided that the following conditions are met:
 * 
 *     * Redistributions of source code must retain the above copyright notice,
 * this list of conditions and the following disclaimer.
 *     * Redistributions in binary form must reproduce the above copyright notice,
 * this list of conditions and the following disclaimer in the documentation and/or
 * other materials provided with the distribution.
 *     * Neither the name of Atlassian Software Systems Pty Ltd nor the names of
 * its contributors may be used to endorse or promote products derived from this
 * software without specific prior written permission.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR
 * ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
 * ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package com.ilmlife.util.profiling;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.ilmlife.util.container.Holder;

/**
 * Bean to contain information about the pages profiled
 *
 * @author <a href="mailto:mike@atlassian.com">Mike Cannon-Brookes</a>
 * @author <a href="mailto:scott@atlassian.com">Scott Farquhar</a>
 * 
 * @version $Date$ $Id$
 */
public class ProfilingTimerBean implements java.io.Serializable {

	private static final long serialVersionUID = -6180672043920208784L;

	List<ProfilingTimerBean> children = new ArrayList<ProfilingTimerBean>();
	ProfilingTimerBean parent = null;

	String resource;

	long startTime;
	long totalTime;

	public ProfilingTimerBean(String resource) {
		this.resource = resource;
	}

	protected void addParent(ProfilingTimerBean parent) {
		this.parent = parent;
	}

	public ProfilingTimerBean getParent() {
		return parent;
	}

	public void addChild(ProfilingTimerBean child) {
		children.add(child);
		child.addParent(this);
	}

	public void setStartTime() {
		this.startTime = System.currentTimeMillis();
	}

	public void setEndTime() {
		this.totalTime = System.currentTimeMillis() - startTime;
	}

	public String getResource() {
		return resource;
	}

	/**
	 * Get a formatted string representing all the methods that took longer than
	 * a specified time.
	 */

	public String getPrintable(String indent, Holder<Boolean> isSlow) {
		StringBuffer buffer = new StringBuffer();
		buffer.append(indent);
		if (totalTime >= ProfilerConf.getInstance().getSlowTimeMs()) {
			buffer.append("[" + totalTime + "ms] - SLOW - " + resource);
			isSlow.setValue(true);
		} else {
			buffer.append("[" + totalTime + "ms] - " + resource);
		}
		buffer.append("\n");

		Iterator<ProfilingTimerBean> childrenIt = children.iterator();
		while (childrenIt.hasNext()) {
			buffer.append(((ProfilingTimerBean) childrenIt.next()).getPrintable(indent + "  ", isSlow));
		}

		return buffer.toString();
	}

}