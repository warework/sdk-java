package com.warework.module.marketing.twitter;

import java.util.Map;
import java.util.ResourceBundle;

import com.warework.core.scope.ScopeFacade;
import com.warework.core.util.CommonValueL2Constants;
import com.warework.module.business.api.ApiException;
import com.warework.module.business.api.ApiRate;
import com.warework.module.marketing.message.GrammarHelper;
import com.warework.module.marketing.message.MessageGrammar;

import twitter4j.IDs;
import twitter4j.Relationship;
import twitter4j.User;

/**
 * 
 * @author Jose Schiaffino
 * @version ${project.version}
 */
public final class TwitterHelper {

	// ///////////////////////////////////////////////////////////////////
	// ATTRIBUTES
	// ///////////////////////////////////////////////////////////////////

	//
	private TwitterHandler handler;

	//
	private TwitterRateController rateController;

	//
	private TwitterDelegate cache;

	// Last time cache was updated.
	private long cacheStartTime;

	// ///////////////////////////////////////////////////////////////////
	// CONSTRUCTORS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * 
	 */
	private TwitterHelper() {
		cacheStartTime = System.currentTimeMillis();
	}

	/**
	 * 
	 * @param scope
	 * @param consumerKey
	 * @param consumerSecret
	 * @param accessToken
	 * @param accessTokenSecret
	 * @throws ApiException
	 */
	public TwitterHelper(final ScopeFacade scope, final String consumerKey, final String consumerSecret,
			final String accessToken, final String accessTokenSecret) throws ApiException {
		this(scope, consumerKey, consumerSecret, accessToken, accessTokenSecret, -1);
	}

	/**
	 * 
	 * @param scope
	 * @param consumerKey
	 * @param consumerSecret
	 * @param accessToken
	 * @param accessTokenSecret
	 * @param timeFrame
	 * @throws ApiException
	 */
	public TwitterHelper(final ScopeFacade scope, final String consumerKey, final String consumerSecret,
			final String accessToken, final String accessTokenSecret, final long timeFrame) throws ApiException {

		//
		this();

		//
		handler = new TwitterHandler(scope, consumerKey, consumerSecret, accessToken, accessTokenSecret);

		//
		rateController = new TwitterRateController(scope, handler, timeFrame);

		//
		cache = new TwitterCache(rateController);

	}

	// ///////////////////////////////////////////////////////////////////
	// PUBLIC METHODS
	// ///////////////////////////////////////////////////////////////////

	/*
	 * ACCOUNT USER OPERATIONS
	 */

	/**
	 * Gets the user of the account logged into Twitter.
	 * 
	 * @return User of the account logged into Twitter.<br>
	 *         <br>
	 * @throws ApiException
	 */
	public User getAuthenticatedUser() throws ApiException {
		return getTwitterDelegate().showUser(getAuthenticatedUserId());
	}

	/**
	 * Gets the ID of the user of the account logged into Twitter.
	 * 
	 * @return ID of the user of the account logged into Twitter.<br>
	 *         <br>
	 * @throws ApiException
	 */
	public long getAuthenticatedUserId() throws ApiException {
		return getTwitterDelegate().getAuthenticatingUserId();
	}

	/**
	 * Gets the name of the user of the account logged into Twitter.
	 * 
	 * @return Name of the user of the account logged into Twitter.<br>
	 *         <br>
	 * @throws ApiException
	 */
	public String getAuthenticatedUserScreenName() throws ApiException {
		return getTwitterDelegate().getAuthenticatingUserScreenName();
	}

	/**
	 * 
	 * @param message
	 * @throws ApiException
	 */
	public void tweet(final String message) throws ApiException {
		getTwitterDelegate().updateStatus(message);
	}

	/**
	 * 
	 * @param bundle
	 * @param baseMessageKey
	 * @param variables
	 * @param messageBlocks
	 * @param hashKey
	 * @throws ApiException
	 */
	public void tweet(final ResourceBundle bundle, final String baseMessageKey, final Map<String, String> variables,
			final MessageGrammar[] messageBlocks, final String hashKey) throws ApiException {
		tweet(GrammarHelper.buildMessage(bundle, baseMessageKey, variables, messageBlocks, hashKey));
	}

	/**
	 * 
	 * @param userId
	 * @throws ApiException
	 */
	public void follow(final long userId) throws ApiException {
		getTwitterDelegate().createFriendship(userId);
	}

	/**
	 * 
	 * @param userId
	 * @throws ApiException
	 */
	public void unfollow(final long userId) throws ApiException {
		getTwitterDelegate().destroyFriendship(userId);
	}

	/**
	 * Decides if an user follows the user of the account logged into Twitter.
	 * 
	 * @param userId ID of the user to check as a follower.<br>
	 *               <br>
	 * @return <code>true</code> if the user of the account logged into Twitter.<br>
	 *         <br>
	 * @throws ApiException
	 */
	public boolean isAuthenticatedUserFollower(final long userId) throws ApiException {

		//
		final Relationship relationship = getTwitterDelegate().showFriendship(getAuthenticatedUserId(), userId);

		//
		if (relationship == null) {
			return false;
		} else {
			return relationship.isTargetFollowingSource();
		}

	}

	/**
	 * Decides if the user of the account logged into Twitter is following another
	 * Twitter user.
	 * 
	 * @param userId ID of the user to check if the user of the account logged into
	 *               Twitter if following to.<br>
	 *               <br>
	 * @return
	 * @throws ApiException
	 */
	public boolean isAuthenticatedUserFollowingUser(final long userId) throws ApiException {

		//
		final Relationship relationship = getTwitterDelegate().showFriendship(getAuthenticatedUserId(), userId);

		//
		if (relationship == null) {
			return false;
		} else {
			return relationship.isSourceFollowingTarget();
		}

	}
	// ///////////////////////////////////////////////////////////////////

	/*
	 * NON ACCOUNT COMMON OPERATIONS
	 */

	/**
	 * 
	 * @param userId
	 * @return
	 * @throws ApiException
	 */
	public User getUser(final long userId) throws ApiException {
		return getTwitterDelegate().showUser(userId);
	}

	/**
	 * 
	 * @param userScreenName
	 * @return
	 * @throws ApiException
	 */
	public User getUser(final String userScreenName) throws ApiException {
		return getTwitterDelegate().showUser(userScreenName);
	}

	// ///////////////////////////////////////////////////////////////////

	/*
	 * RELATIONSHIP OPERATIONS
	 */

	/**
	 * Decides if source user is following target user.
	 * 
	 * @param source
	 * @param target
	 * @return
	 * @throws ApiException
	 */
	public boolean isSourceFollowingTarget(final long source, final long target) throws ApiException {

		//
		final Relationship relationship = getTwitterDelegate().showFriendship(source, target);

		//
		if (relationship == null) {
			return false;
		} else {
			return (relationship.isSourceFollowingTarget());
		}

	}

	/**
	 * Decides if target user is following source user.
	 * 
	 * @param source
	 * @param target
	 * @return
	 * @throws ApiException
	 */
	public boolean isTargetFollowingSource(final long source, final long target) throws ApiException {

		//
		final Relationship relationship = getTwitterDelegate().showFriendship(source, target);

		//
		if (relationship == null) {
			return false;
		} else {
			return (relationship.isTargetFollowingSource());
		}

	}

	/**
	 * Decides if an user follows another user and vice versa.
	 * 
	 * @param userId1
	 * @param userId2
	 * @return
	 * @throws ApiException
	 */
	public boolean existsFriendship(final long userId1, final long userId2) throws ApiException {

		//
		final Relationship relationship = getTwitterDelegate().showFriendship(userId1, userId2);

		//
		if (relationship == null) {
			return false;
		} else {
			return ((relationship.isSourceFollowingTarget()) && (relationship.isTargetFollowingSource()));
		}

	}

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
	public IDs getFollowersIDs(final long userId, final long cursor) throws ApiException {
		return getTwitterDelegate().getFollowersIDs(userId, cursor);
	}

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
	public IDs getFollowedIDs(final long userId, final long cursor) throws ApiException {
		return getTwitterDelegate().getFriendsIDs(userId, cursor);
	}

	// ///////////////////////////////////////////////////////////////////

	/**
	 * 
	 * @param operationType
	 * @return
	 */
	public ApiRate getAPIRate(final TwitterOperation operationType) {
		return rateController.getApiRate(operationType);
	}

	// ///////////////////////////////////////////////////////////////////

	/**
	 * Gets a string with the code that represents an emoji integer.
	 * 
	 * @param number     Any integer value from 0 to 99. <br>
	 *                   <br>
	 * @param prefixZero Add a zero on values less than 10 so the result will be 00,
	 *                   01, 02, 03 and so on. <br>
	 *                   <br>
	 * @return Character codes that represents a emoji number.
	 */
	public static String toEmoji(final int number, final boolean prefixZero) {

		// Initially, no zero prefix.
		String tens = CommonValueL2Constants.STRING_EMPTY;

		// Integer part division.
		final int result = number / 10;

		// Integer rest division.
		final int rest = number % 10;

		// Emoji code values.
		final int unitCode = 48 + rest;
		final int tensCode = 48 + result;

		// Unit. Always exists
		final String units = new String(Character.toChars(unitCode)) + new String(Character.toChars(0x20E3));

		// Tens (if exists)
		if (prefixZero) {
			tens = new String(Character.toChars(tensCode)) + new String(Character.toChars(0x20E3));
		}

		// Return emoji code.
		return tens + units;

	}

	// ///////////////////////////////////////////////////////////////////
	// PRIVATE METHODS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * 
	 * @return
	 */
	private TwitterDelegate getTwitterDelegate() {

		//
		if ((System.currentTimeMillis() - cacheStartTime) >= TwitterConstants.CACHE_DEFAULT_LIFETIME_HELPER) {

			//
			cache = new TwitterCache(rateController);

			// Set authenticating user cache time.
			cacheStartTime = System.currentTimeMillis();

		}

		//
		return cache;

	}

}
