package com.warework.module.marketing.twitter;

import com.warework.core.scope.ScopeFacade;
import com.warework.module.business.api.ApiException;
import com.warework.module.business.api.ApiRate;
import com.warework.module.business.api.ApiRateLimitException;
import com.warework.service.log.LogServiceConstants;

import twitter4j.IDs;
import twitter4j.Relationship;
import twitter4j.User;

/**
 * 
 * @author Jose Schiaffino
 * @version ${project.version}
 */
final class TwitterRateController implements TwitterDelegate {

	// ///////////////////////////////////////////////////////////////////
	// ATTRIBUTES
	// ///////////////////////////////////////////////////////////////////

	//
	private ScopeFacade scope;

	//
	private TwitterDelegate delegate;

	//
	private TwitterRate twitterRate;

	// ///////////////////////////////////////////////////////////////////
	// CONSTRUCTORS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * 
	 */
	private TwitterRateController() {
		// DO NOTHING.
	}

	/**
	 * 
	 * @param scope
	 * @param handler
	 * @param timeFrame
	 * @throws ApiException
	 */
	TwitterRateController(final ScopeFacade scope, final TwitterHandler handler, final long timeFrame)
			throws ApiException {

		//
		this();

		//
		this.scope = scope;

		//
		delegate = handler;

		//
		twitterRate = new TwitterRate(handler, timeFrame);

	}

	// ///////////////////////////////////////////////////////////////////
	// PUBLIC METHODS
	// ///////////////////////////////////////////////////////////////////

	public long getAuthenticatingUserId() throws ApiException {
		return delegate.getAuthenticatingUserId();
	}

	public String getAuthenticatingUserScreenName() throws ApiException {
		return delegate.getAuthenticatingUserScreenName();
	}

	public void updateStatus(final String message) throws ApiException {
		if (twitterRate.increase(TwitterOperation.POST_UPDATE_STATUS)) {
			delegate.updateStatus(message);
		} else {
			throw createException(
					"WAREWORK cannot execute Twitter update status operation because rate limit has been exceeded.");
		}
	}

	public void createFriendship(final long userId) throws ApiException {
		if (twitterRate.increase(TwitterOperation.POST_CREATE_FRIENDSHIP)) {
			delegate.createFriendship(userId);
		} else {
			throw createException(
					"WAREWORK cannot execute Twitter create friendship operation because rate limit has been exceeded.");
		}
	}

	public void destroyFriendship(final long userId) throws ApiException {
		if (twitterRate.increase(TwitterOperation.POST_DESTROY_FRIENDSHIP)) {
			delegate.destroyFriendship(userId);
		} else {
			throw createException(
					"WAREWORK cannot execute Twitter destroy friendship operation because rate limit has been exceeded.");
		}
	}

	public User showUser(final long userId) throws ApiException {
		if (userId == getAuthenticatingUserId()) {
			return twitterRate.getAuthenticatingUser();
		} else if (twitterRate.increase(TwitterOperation.GET_USER_SHOW)) {
			return delegate.showUser(userId);
		} else {
			throw createException(
					"WAREWORK cannot execute Twitter show user operation because rate limit has been exceeded.");
		}
	}

	public User showUser(final String userScreenName) throws ApiException {
		if (userScreenName.equals(getAuthenticatingUserScreenName())) {
			return twitterRate.getAuthenticatingUser();
		} else if (twitterRate.increase(TwitterOperation.GET_USER_SHOW)) {
			return delegate.showUser(userScreenName);
		} else {
			throw createException(
					"WAREWORK cannot execute Twitter show user operation because rate limit has been exceeded.");
		}
	}

	public Relationship showFriendship(final long userId1, final long userId2) throws ApiException {
		if (twitterRate.increase(TwitterOperation.GET_FRIENDSHIP_SHOW)) {
			return delegate.showFriendship(userId1, userId2);
		} else {
			throw createException(
					"WAREWORK cannot execute Twitter show friendship operation because rate limit has been exceeded.");
		}
	}

	public IDs getFollowersIDs(final long userId, final long cursor) throws ApiException {
		if (twitterRate.increase(TwitterOperation.GET_FOLLOWERS_IDS)) {
			return delegate.getFollowersIDs(userId, cursor);
		} else {
			throw createException(
					"WAREWORK cannot execute Twitter get followers IDs operation because rate limit has been exceeded.");
		}
	}

	public IDs getFriendsIDs(final long userId, final long cursor) throws ApiException {
		if (twitterRate.increase(TwitterOperation.GET_FRIENDS_IDS)) {
			return delegate.getFriendsIDs(userId, cursor);
		} else {
			throw createException(
					"WAREWORK cannot execute Twitter get friends IDs operation because rate limit has been exceeded.");
		}
	}

	// ///////////////////////////////////////////////////////////////////
	// PACKAGE METHODS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * 
	 * @param operationType
	 * @return
	 */
	ApiRate getApiRate(final TwitterOperation operationType) {
		return twitterRate.getApiRate(operationType);
	}

	// ///////////////////////////////////////////////////////////////////
	// PRIVATE METHODS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * 
	 * @param message
	 * @return
	 */
	private ApiException createException(final String message) {
		return new ApiRateLimitException(scope, message, null, LogServiceConstants.LOG_LEVEL_WARN);
	}

}
