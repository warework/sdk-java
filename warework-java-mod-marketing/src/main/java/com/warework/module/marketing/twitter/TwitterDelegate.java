package com.warework.module.marketing.twitter;

import com.warework.module.business.api.ApiException;

import twitter4j.IDs;
import twitter4j.Relationship;
import twitter4j.User;

/**
 * 
 * @author Jose Schiaffino
 * @version ${project.version}
 */
interface TwitterDelegate {

	/*
	 * AUTHENTICATING USER OPERATIONS
	 */

	/**
	 * Gets the ID of the user of the account logged into Twitter.
	 * 
	 * @return ID of the user of the account logged into Twitter.<br>
	 *         <br>
	 * @throws ApiException
	 */
	long getAuthenticatingUserId() throws ApiException;

	/**
	 * Gets the name of the user of the account logged into Twitter.
	 * 
	 * @return Name of the user of the account logged into Twitter.<br>
	 *         <br>
	 * @throws ApiException
	 */
	String getAuthenticatingUserScreenName() throws ApiException;

	/**
	 * 
	 * @param message
	 * @throws ApiException
	 */
	void updateStatus(final String message) throws ApiException;

	/**
	 * 
	 * @param userId
	 * @throws ApiException
	 */
	void createFriendship(final long userId) throws ApiException;

	/**
	 * 
	 * @param userId
	 * @throws ApiException
	 */
	void destroyFriendship(final long userId) throws ApiException;

	// ///////////////////////////////////////////////////////////////////

	/*
	 * NON AUTHENTICATING USER COMMON OPERATIONS
	 */

	/**
	 * 
	 * @param userId
	 * @return
	 * @throws ApiException
	 */
	User showUser(final long userId) throws ApiException;

	/**
	 * 
	 * @param userScreenName
	 * @return
	 * @throws ApiException
	 */
	User showUser(final String userScreenName) throws ApiException;

	// ///////////////////////////////////////////////////////////////////

	/*
	 * RELATIONSHIP OPERATIONS
	 */

	/**
	 * 
	 * @param userId1
	 * @param userId2
	 * @return
	 * @throws ApiException
	 */
	Relationship showFriendship(final long userId1, final long userId2) throws ApiException;

	/**
	 * Get the IDs of every Twitter user that follows another user.
	 * 
	 * @param userId ID of the Twitter user where to retrieve the list of
	 *               followers.<br>
	 *               <br>
	 * @param cursor Causes the list of connections to be broken into pages of no
	 *               more than 5000 IDs at a time. The number of IDs returned is not
	 *               guaranteed to be 5000 as suspended users are filtered out after
	 *               connections are queried. To begin paging provide a value of -1
	 *               as the cursor. The response from the API will include a
	 *               previous_cursor and next_cursor to allow paging back and
	 *               forth.<br>
	 *               <br>
	 * @return
	 * @throws ApiException
	 */
	IDs getFollowersIDs(final long userId, final long cursor) throws ApiException;

	/**
	 * Get the IDs of every Twitter user which are followed by an user.
	 * 
	 * @param userId Specifies the ID of the user for whom to return the friends
	 *               list.<br>
	 *               <br>
	 * @param cursor
	 * @return
	 * @throws ApiException
	 */
	IDs getFriendsIDs(final long userId, final long cursor) throws ApiException;

}
