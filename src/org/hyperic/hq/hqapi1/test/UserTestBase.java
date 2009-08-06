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

package org.hyperic.hq.hqapi1.test;

import org.hyperic.hq.hqapi1.UserApi;
import org.hyperic.hq.hqapi1.types.User;
import org.hyperic.hq.hqapi1.types.UserResponse;
import org.hyperic.hq.hqapi1.types.UsersResponse;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public abstract class UserTestBase extends HQApiTestBase {

    static final String PASSWORD = "apitest";

    static final String TESTUSER_NAME_PREFIX = "apitest";
    static final String TESTUSER_FIRSTNAME   = "API";
    static final String TESTUSER_LASTNAME    = "Test";
    static final String TESTUSER_EMAIL       = "apitest@hyperic.com";
    static final boolean TESTUSER_ACTIVE     = true;

    public UserTestBase(String name) {
        super(name);
    }

    UserApi getUserApi() {
        return getApi().getUserApi();
    }

    UserApi getUserApi(String user, String password) {
        return getApi(user, password).getUserApi();
    }

    /**
     * Generate a valid User object that's guaranteed to have a unique Name
     * @return A valid User object.
     */
    public User generateTestUser() {

        Random r = new Random();

        User user = new User();
        user.setName(TESTUSER_NAME_PREFIX + r.nextInt());
        user.setFirstName(TESTUSER_FIRSTNAME);
        user.setLastName(TESTUSER_LASTNAME);
        user.setEmailAddress(TESTUSER_EMAIL);
        user.setActive(TESTUSER_ACTIVE);
        return user;
    }

    /**
     * Create a List of Users.
     *
     * @param num The number of users to generate
     */
    public List<User> createTestUsers(int num) throws Exception {
        ArrayList<User> users = new ArrayList<User>();
        for (int i = 0; i < num; i++) {
            User u = generateTestUser();
            UserResponse createResponse = getUserApi().createUser(u, PASSWORD);
            hqAssertSuccess(createResponse);
            users.add(createResponse.getUser());
        }
        return users;
    }

    /**
     * Clean up test users after each test run.
     */
    public void tearDown() throws Exception {

        UserApi api = getUserApi();
        UsersResponse response = api.getUsers();

        for (User u : response.getUser()) {
            if (u.getName().startsWith(TESTUSER_NAME_PREFIX)) {
                api.deleteUser(u.getId());
            }
        }

        super.tearDown();
    }
}
