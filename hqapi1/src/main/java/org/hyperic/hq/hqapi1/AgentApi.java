/*
 * 
 * NOTE: This copyright does *not* cover user programs that use HQ
 * program services by normal system calls through the application
 * program interfaces provided as part of the Hyperic Plug-in Development
 * Kit or the Hyperic Client Development Kit - this is merely considered
 * normal use of the program, and does *not* fall under the heading of
 * "derived work".
 * 
 * Copyright (C) [2008, 2009], Hyperic, Inc.
 * This file is part of HQ.
 * 
 * HQ is free software; you can redistribute it and/or modify
 * it under the terms version 2 of the GNU General Public License as
 * published by the Free Software Foundation. This program is distributed
 * in the hope that it will be useful, but WITHOUT ANY WARRANTY; without
 * even the implied warranty of MERCHANTABILITY or FITNESS FOR A
 * PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307
 * USA.
 * 
 */

package org.hyperic.hq.hqapi1;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.hyperic.hq.hqapi1.types.Agent;
import org.hyperic.hq.hqapi1.types.AgentResponse;
import org.hyperic.hq.hqapi1.types.AgentsResponse;
import org.hyperic.hq.hqapi1.types.PingAgentResponse;
import org.hyperic.hq.hqapi1.types.StatusResponse;
import org.hyperic.hq.hqapi1.types.AgentBundleFilesResponse;
import org.hyperic.hq.hqapi1.types.AgentBundleNameResponse;

/**
 * The Hyperic HQ Agent API.
 * <br><br>
 * This class provides access to the agents within the HQ system.  Each of the
 * methods in this class return {@link org.hyperic.hq.hqapi1.types.Response}
 * objects that wrap the result of the method with a
 * {@link org.hyperic.hq.hqapi1.types.ResponseStatus} and a
 * {@link org.hyperic.hq.hqapi1.types.ServiceError} that indicates the error
 * if the response status is {@link org.hyperic.hq.hqapi1.types.ResponseStatus#FAILURE}.
 */
public class AgentApi extends BaseApi {

    public AgentApi(HQConnection connection) {
        super(connection);
    }

    /**
     * Get an {@link Agent} by id.
     *
     * @param id The id of the Agent to get
     *
     * @return  On {@link org.hyperic.hq.hqapi1.types.ResponseStatus#SUCCESS},
     * a the requested agent is returned via
     * {@link org.hyperic.hq.hqapi1.types.AgentResponse#getAgent()}.
     *
     * @throws java.io.IOException If a network error occurs while making the request.
     */
    public AgentResponse getAgent(int id)
        throws IOException
    {
        Map<String,String[]> params = new HashMap<String,String[]>();
        params.put("id", new String[] { String.valueOf(id) });
        return doGet("agent/get.hqu", params, 
                     new XmlResponseHandler<AgentResponse>(AgentResponse.class));
    }

    /**
     * Get an {@link Agent} by agent token.
     *
     * @param agentToken The agent token of the requested agent.
     *
     * @return  On {@link org.hyperic.hq.hqapi1.types.ResponseStatus#SUCCESS},
     * a the requested agent is returned via
     * {@link org.hyperic.hq.hqapi1.types.AgentResponse#getAgent()}.
     *
     * @throws java.io.IOException If a network error occurs while making the request.
     */
    public AgentResponse getAgent(String agentToken)
        throws IOException
    {
        Map<String,String[]> params = new HashMap<String,String[]>();
        params.put("agentToken", new String[] { agentToken });
        return doGet("agent/get.hqu", params, 
                     new XmlResponseHandler<AgentResponse>(AgentResponse.class));
    }
    
    /**
     * Get an {@link Agent} by address and port.
     *
     * @param address The address of the requested agent.  This can be a hostname
     * or IP address.
     * @param port The port of the requested Agent.
     *
     * @return  On {@link org.hyperic.hq.hqapi1.types.ResponseStatus#SUCCESS},
     * a the requested agent is returned via
     * {@link org.hyperic.hq.hqapi1.types.AgentResponse#getAgent()}.
     *
     * @throws java.io.IOException If a network error occurs while making the request.
     */
    public AgentResponse getAgent(String address, int port)
        throws IOException
    {
        Map<String,String[]> params = new HashMap<String,String[]>();
        params.put("address", new String[] { address });
        params.put("port", new String[] { String.valueOf(port) });
        return doGet("agent/get.hqu", params, 
                     new XmlResponseHandler<AgentResponse>(AgentResponse.class));
    }

    /**
     * Get a list of all {@link Agent}s.
     *
     * @return  On {@link org.hyperic.hq.hqapi1.types.ResponseStatus#SUCCESS},
     * a list of agents is returned via 
     * {@link org.hyperic.hq.hqapi1.types.AgentsResponse#getAgent()}.
     *
     * @throws java.io.IOException If a network error occurs while making the request.
     */
    public AgentsResponse getAgents()
        throws IOException
    {
        return doGet("agent/list.hqu", new HashMap<String,String[]>(),
                     new XmlResponseHandler<AgentsResponse>(AgentsResponse.class));
    }

    /**
     * Ping an {@link Agent}.
     *
     * @param agent The agent to ping.
     * 
     * @return On {@link org.hyperic.hq.hqapi1.types.ResponseStatus#SUCCESS},
     * a the requested agent status is returned via
     * {@link org.hyperic.hq.hqapi1.types.PingAgentResponse#isUp()}.
     * 
     * @throws java.io.IOException If a network error occurs while making the request.
     */
    public PingAgentResponse pingAgent(Agent agent)
        throws IOException
    {
        Map <String,String[]> params = new HashMap<String,String[]>();
        params.put("id", new String[] { String.valueOf(agent.getId()) });
        return doGet("agent/ping.hqu", params,
                     new XmlResponseHandler<PingAgentResponse>(PingAgentResponse.class));
    }

    /**
     * Transfer a plugin to the given {@link Agent}.  This operation is asynchronous
     * and will result in an agent restart.
     *
     * @param agent The agent to ping.
     * @param plugin The full plugin name including the -plugin.jar or -plugin.xml suffix.
     *
     * @return {@link org.hyperic.hq.hqapi1.types.ResponseStatus#SUCCESS} indicates
     * the request was successful.
     *
     * @throws java.io.IOException If a network error occurs while making the request.
     */
    public StatusResponse transferPlugin(Agent agent, String plugin)
        throws IOException
    {
        Map <String,String[]> params = new HashMap<String,String[]>();
        params.put("id", new String[] { String.valueOf(agent.getId()) });
        params.put("plugin", new String[] { plugin });
        return doGet("agent/transferPlugin.hqu", params,
                     new XmlResponseHandler<StatusResponse>(StatusResponse.class));
    }
    
    
    /**
     * List current agent bundles available on server
     *
     * @return On success, returns list of 
     * {@link org.hyperic.hq.hqapi1.types.AgentBundleFilesResponse#getAgentBundleFile}
     * 
     * @throws java.io.IOException If a network error occurs while making the request.
     */
    public AgentBundleFilesResponse bundleList()
        throws IOException
    {
        return doGet("agent/bundleList.hqu", new HashMap<String,String[]>(),
                     new XmlResponseHandler<AgentBundleFilesResponse>(AgentBundleFilesResponse.class));
    }
    
    /**
     * List current agent bundles available on server
     *
     * @param agent The agent to get current bundle
     *
     * @return {@link org.hyperic.hq.hqapi1.types.AgentBundleNameResponse#getAgentBundleName}
     * 
     * @throws java.io.IOException If a network error occurs while making the request.
     */
    public AgentBundleNameResponse bundleStatus(Agent agent)
        throws IOException
    {
        Map <String,String[]> params = new HashMap<String,String[]>();
        params.put("id", new String[] { String.valueOf(agent.getId()) });
        return doGet("agent/bundleStatus.hqu", params,
                     new XmlResponseHandler<AgentBundleNameResponse>(AgentBundleNameResponse.class));
    }

    /**
     * Upgrade bunle on {@link Agent}
     *
     * @param agent The agent to upgradee
     * @param bundle The name of the bundle
     *
     * @return {@link org.hyperic.hq.hqapi1.types.StatusResponse}
     * 
     * @throws java.io.IOException If a network error occurs while making the request.
     */
    public StatusResponse bundlePush(Agent agent, String bundle)
        throws IOException
    {
        Map <String,String[]> params = new HashMap<String,String[]>();
        params.put("id", new String[] { String.valueOf(agent.getId()) });
        params.put("bundle", new String[] { bundle });
        return doGet("agent/bundlePush.hqu", params,
                     new XmlResponseHandler<StatusResponse>(StatusResponse.class));
    }
        
}
