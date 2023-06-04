package com.warework.module.marketing.twitter;

import com.warework.core.scope.ScopeFacade;
import com.warework.module.business.api.ApiException;
import com.warework.service.log.LogServiceConstants;

import twitter4j.IDs;
import twitter4j.Relationship;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.User;
import twitter4j.conf.ConfigurationBuilder;

/**
 * 
 * @author Jose Schiaffino
 * @version 3.0.0
 */
final class TwitterHandler implements TwitterDelegate {

	// ///////////////////////////////////////////////////////////////////
	// ATTRIBUTES
	// ///////////////////////////////////////////////////////////////////

	//
	private ScopeFacade scope;

	//
	private TwitterFactory factory;

	//
	private Twitter connection;

	// ///////////////////////////////////////////////////////////////////
	// CONSTRUCTORS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * 
	 */
	private TwitterHandler() {
		// DO NOTHING.
	}

	/**
	 * 
	 * @param consumerKey
	 * @param consumerSecret
	 * @param accessToken
	 * @param accessTokenSecret
	 */
	TwitterHandler(final ScopeFacade scope, final String consumerKey, final String consumerSecret,
			final String accessToken, final String accessTokenSecret) {

		//
		this();

		//
		this.scope = scope;

		//
		final ConfigurationBuilder config = new ConfigurationBuilder();
		{
			config.setDebugEnabled(true).setOAuthConsumerKey(consumerKey).setOAuthConsumerSecret(consumerSecret)
					.setOAuthAccessToken(accessToken).setOAuthAccessTokenSecret(accessTokenSecret);
		}

		//
		factory = new TwitterFactory(config.build());

	}

	// ///////////////////////////////////////////////////////////////////
	// PUBLIC METHODS
	// ///////////////////////////////////////////////////////////////////

	/*
	 * ACCOUNT OPERATIONS
	 */

	/**
	 * Gets the ID of the user of the account logged into Twitter.
	 * 
	 * @return ID of the user of the account logged into Twitter.<br>
	 *         <br>
	 * @throws ApiException
	 */
	public long getAuthenticatingUserId() throws ApiException {
		try {
			return getConnection().getId();
		} catch (final IllegalStateException e) {
			throw createException("WAREWORK cannot get authenticating user ID because no credentials are supplied.", e);
		} catch (final TwitterException e) {
			throw createException("WAREWORK cannot get authenticating user ID because credentials cannot be verified.",
					e);
		}
	}

	/**
	 * Gets the name of the user of the account logged into Twitter.
	 * 
	 * @return Name of the user of the account logged into Twitter.<br>
	 *         <br>
	 * @throws ApiException
	 */
	public String getAuthenticatingUserScreenName() throws ApiException {
		try {
			return getConnection().getScreenName();
		} catch (final IllegalStateException e) {
			throw createException(
					"WAREWORK cannot get authenticating user screen name because no credentials are supplied.", e);
		} catch (final TwitterException e) {
			throw createException(
					"WAREWORK cannot get authenticating user screen name because credentials cannot be verified.", e);
		}
	}

	/**
	 * 
	 * @param message
	 * @throws ApiException
	 */
	public void updateStatus(final String message) throws ApiException {
		try {
			getConnection().updateStatus(message);
		} catch (final TwitterException e) {
			throw createException("WAREWORK cannot update status because Twitter service reported the following error: "
					+ e.getMessage(), e);
		}
	}

	/**
	 * 
	 * @param userId
	 * @throws ApiException
	 */
	public void createFriendship(final long userId) throws ApiException {
		try {
			getConnection().createFriendship(userId);
		} catch (final TwitterException e) {
			throw createException(
					"WAREWORK cannot create friendship because Twitter service reported the following error: "
							+ e.getMessage(),
					e);
		}
	}

	/**
	 * 
	 * @param userId
	 * @throws ApiException
	 */
	public void destroyFriendship(final long userId) throws ApiException {
		try {
			getConnection().destroyFriendship(userId);
		} catch (final TwitterException e) {
			throw createException(
					"WAREWORK cannot destroy friendship because Twitter service reported the following error: "
							+ e.getMessage(),
					e);
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
	public User showUser(final long userId) throws ApiException {
		try {
			return getConnection().showUser(userId);
		} catch (final TwitterException e) {
			if (e.getErrorCode() == TwitterConstants.ERROR_CODE_USER_NOT_FOUND) {
				return null;
			} else if (e.getErrorCode() == TwitterConstants.ERROR_CODE_USER_ACCOUNT_SUSPENDED) {
				return null;
			} else {
				throw createException(
						"WAREWORK cannot show user data because Twitter service reported the following error: "
								+ e.getMessage(),
						e);
			}
		}
	}

	/**
	 * 
	 * @param userScreenName
	 * @return
	 * @throws ApiException
	 */
	public User showUser(final String userScreenName) throws ApiException {
		try {
			return getConnection().showUser(userScreenName);
		} catch (final TwitterException e) {
			if (e.getErrorCode() == TwitterConstants.ERROR_CODE_USER_NOT_FOUND) {
				return null;
			} else if (e.getErrorCode() == TwitterConstants.ERROR_CODE_USER_ACCOUNT_SUSPENDED) {
				return null;
			} else {
				throw createException(
						"WAREWORK cannot show user data because Twitter service reported the following error: "
								+ e.getMessage(),
						e);
			}
		}
	}

	// ///////////////////////////////////////////////////////////////////

	/*
	 * RELATIONSHIP OPERATIONS
	 */

	/**
	 * 
	 * @param sourceUserId
	 * @param targetUserId
	 * @return
	 * @throws ApiException
	 */
	public Relationship showFriendship(final long sourceUserId, final long targetUserId) throws ApiException {
		try {
			return getConnection().showFriendship(sourceUserId, targetUserId);
		} catch (final TwitterException e) {
			throw createException(
					"WAREWORK cannot show friendship data because Twitter service reported the following error: "
							+ e.getMessage(),
					e);
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
		try {
			return getConnection().getFollowersIDs(userId, cursor);
		} catch (final TwitterException e) {
			throw createException(
					"WAREWORK cannot get followers IDs because Twitter service reported the following error: "
							+ e.getMessage(),
					e);
		}
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
	public IDs getFriendsIDs(final long userId, final long cursor) throws ApiException {
		try {
			return getConnection().getFriendsIDs(userId, cursor);
		} catch (final TwitterException e) {
			throw createException(
					"WAREWORK cannot get friends IDs because Twitter service reported the following error: "
							+ e.getMessage(),
					e);
		}
	}

	// ///////////////////////////////////////////////////////////////////
	// PRIVATE METHODS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * 
	 * @return
	 */
	private Twitter getConnection() {

		//
		if (connection == null) {
			connection = factory.getInstance();
		}

		//
		return connection;

	}

	/**
	 * 
	 * @param e
	 * @return
	 */
	private ApiException createException(final String message, final Exception e) {
		return new ApiException(scope, message, e, LogServiceConstants.LOG_LEVEL_WARN);
	}

}
