package com.warework.module.marketing.twitter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.warework.module.business.api.ApiException;

import twitter4j.IDs;
import twitter4j.Relationship;
import twitter4j.User;

/**
 * 
 * @author Jose Schiaffino
 * @version 3.0.0
 */
final class TwitterCache implements TwitterDelegate {

	// ///////////////////////////////////////////////////////////////////
	// INNER CLASSES
	// ///////////////////////////////////////////////////////////////////

	/**
	 * 
	 * @author jschiaffino
	 *
	 */
	private class RelationshipCache {

		// ///////////////////////////////////////////////////////////////
		// ATTRIBUTES
		// ///////////////////////////////////////////////////////////////

		//
		private long targetUserId;

		//
		private Relationship relationship;

		// ///////////////////////////////////////////////////////////////
		// CONSTRUCTORS
		// ///////////////////////////////////////////////////////////////

		/**
		 * 
		 * @param targetUserId
		 * @param relationship
		 */
		RelationshipCache(final long targetUserId, final Relationship relationship) {
			this.targetUserId = targetUserId;
			this.relationship = relationship;
		}

		// ///////////////////////////////////////////////////////////////
		// METHODS
		// ///////////////////////////////////////////////////////////////

		/**
		 * 
		 * @return
		 */
		long getTargetUserId() {
			return targetUserId;
		}

		/**
		 * 
		 * @return
		 */
		Relationship getRelationship() {
			return relationship;
		}

	}

	// ///////////////////////////////////////////////////////////////////
	// ATTRIBUTES
	// ///////////////////////////////////////////////////////////////////

	//
	private final Map<Long, User> cacheById = new HashMap<Long, User>();

	//
	private final Map<String, User> cacheByName = new HashMap<String, User>();

	//
	private final Map<Long, Collection<RelationshipCache>> relationships = new HashMap<Long, Collection<RelationshipCache>>();

	//
	private long authenticatingUserId = -1;

	//
	private String authenticatingUserName;

	//
	private TwitterDelegate delegate;

	// ///////////////////////////////////////////////////////////////////
	// CONSTRUCTORS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * 
	 */
	private TwitterCache() {

	}

	/**
	 * 
	 * @param twitter
	 */
	TwitterCache(final TwitterDelegate twitter) {

		//
		this();

		//
		this.delegate = twitter;

	}

	// ///////////////////////////////////////////////////////////////////
	// PUBLIC METHODS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * Gets the ID of the user of the account logged into Twitter.
	 * 
	 * @return ID of the user of the account logged into Twitter.<br>
	 *         <br>
	 * @throws ApiException
	 */
	public long getAuthenticatingUserId() throws ApiException {

		//
		if (authenticatingUserId == -1) {
			authenticatingUserId = delegate.getAuthenticatingUserId();
		}

		//
		return authenticatingUserId;

	}

	/**
	 * Gets the name of the user of the account logged into Twitter.
	 * 
	 * @return Name of the user of the account logged into Twitter.<br>
	 *         <br>
	 * @throws ApiException
	 */
	public String getAuthenticatingUserScreenName() throws ApiException {

		//
		if (authenticatingUserName == null) {
			authenticatingUserName = delegate.getAuthenticatingUserScreenName();
		}

		//
		return authenticatingUserName;

	}

	/**
	 * 
	 * @param sourceUserId
	 * @param targetUserId
	 * @return
	 * @throws ApiException
	 */
	public Relationship showFriendship(final long sourceUserId, final long targetUserId) throws ApiException {

		//
		Relationship relationship = findCacheRelationship(sourceUserId, targetUserId);

		//
		if (relationship == null) {

			//
			relationship = delegate.showFriendship(sourceUserId, targetUserId);

			//
			if (!relationships.containsKey(sourceUserId)) {
				relationships.put(sourceUserId, new ArrayList<RelationshipCache>());
			}

			//
			relationships.get(sourceUserId).add(new RelationshipCache(targetUserId, relationship));

		}

		//
		return relationship;

	}

	/**
	 * 
	 * @param userId
	 * @return
	 * @throws ApiException
	 */
	public User showUser(final long userId) throws ApiException {

		//
		if ((delegate instanceof TwitterRateController) && (userId == getAuthenticatingUserId())) {
			return delegate.showUser(userId);
		}

		//
		if (!cacheById.containsKey(userId)) {

			//
			final User user = delegate.showUser(userId);

			//
			if (user == null) {
				return null;
			} else {

				//
				cacheById.put(userId, user);

				//
				cacheByName.put(user.getName(), user);

			}

		}

		//
		return cacheById.get(userId);

	}

	/**
	 * 
	 * @param userScreenName
	 * @return
	 * @throws ApiException
	 */
	public User showUser(final String userScreenName) throws ApiException {

		//
		if ((delegate instanceof TwitterRateController) && (userScreenName.equals(getAuthenticatingUserScreenName()))) {
			return delegate.showUser(userScreenName);
		}

		//
		if (!cacheByName.containsKey(userScreenName)) {

			//
			final User user = delegate.showUser(userScreenName);

			//
			if (user == null) {
				return null;
			} else {

				//
				cacheById.put(user.getId(), user);

				//
				cacheByName.put(userScreenName, user);

			}

		}

		//
		return cacheByName.get(userScreenName);

	}

	// ///////////////////////////////////////////////////////////////////

	/*
	 * NON-CACHEABLE OPERATIONS
	 */

	/**
	 * 
	 * @param message
	 * @throws ApiException
	 */
	public void updateStatus(final String message) throws ApiException {
		delegate.updateStatus(message);
	}

	/**
	 * 
	 * @param userId
	 * @throws ApiException
	 */
	public void createFriendship(final long userId) throws ApiException {
		delegate.createFriendship(userId);
	}

	/**
	 * 
	 * @param userId
	 * @throws ApiException
	 */
	public void destroyFriendship(final long userId) throws ApiException {
		delegate.destroyFriendship(userId);
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
		return delegate.getFollowersIDs(userId, cursor);
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
		return delegate.getFriendsIDs(userId, cursor);
	}

	// ///////////////////////////////////////////////////////////////////
	// PRIVATE METHODS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * 
	 * @param sourceUserId
	 * @param targetUserId
	 * @return
	 */
	private Relationship findCacheRelationship(final long sourceUserId, final long targetUserId) {

		//
		final Collection<RelationshipCache> userRelationships = relationships.get(sourceUserId);

		//
		if (userRelationships != null) {
			for (final Iterator<RelationshipCache> iterator = userRelationships.iterator(); iterator.hasNext();) {

				//
				final RelationshipCache relationshipCache = iterator.next();

				//
				if (relationshipCache.getTargetUserId() == targetUserId) {
					return relationshipCache.getRelationship();
				}

			}
		}

		//
		return null;

	}

}
