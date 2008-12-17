package org.hyperic.hq.hqapi1.tools;

import org.hyperic.hq.hqapi1.HQApi;
import org.hyperic.hq.hqapi1.UserApi;
import org.hyperic.hq.hqapi1.XmlUtil;
import org.hyperic.hq.hqapi1.types.GetUsersResponse;
import org.hyperic.hq.hqapi1.types.User;
import org.hyperic.hq.hqapi1.types.SyncUsersResponse;

import java.util.List;

import joptsimple.OptionParser;
import joptsimple.OptionSet;

public class UserSync extends ToolsBase {

    private static void syncUsers(String[] args) throws Exception {

        OptionParser p = getOptionParser();
        OptionSet options = getOptions(p, args);

        HQApi api = getApi(options);

        UserApi userApi = api.getUserApi();

        GetUsersResponse resp = XmlUtil.deserialize(GetUsersResponse.class,
                                                    System.in);
        List<User> users = resp.getUser();

        SyncUsersResponse syncResponse = userApi.syncUsers(users);
        checkSuccess(syncResponse);

        System.out.println("Successfully synced " + users.size() + " users.");
    }
    
    public static void main(String[] args) throws Exception {
        try {
            syncUsers(args);
        } catch (Exception e) {
            System.err.println("Error listing users: " + e.getMessage());
            System.exit(-1);
        }
    }
}