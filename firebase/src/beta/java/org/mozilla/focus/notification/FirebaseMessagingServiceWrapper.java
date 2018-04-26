/* -*- Mode: Java; c-basic-offset: 4; tab-width: 4; indent-tabs-mode: nil; -*-
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package org.mozilla.focus.notification;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;


/**
 * Provide the actual impl (FirebaseMessagingService) or the dummy one (Service) so we can use the module
 * accordingly with our build types (currently only debug and beta will provide actual impl)
 *
 * If actual impl is provided, it'll handle the push and call onRemoteMessage().
 */
abstract public class FirebaseMessagingServiceWrapper extends FirebaseMessagingService {

    public static final String PUSH_OPEN_URL = "push_open_url";

    abstract public void onRemoteMessage(String url, String title, String body);

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        // Check if message contains a notification payload.
        if (remoteMessage.getNotification() != null) {

            String url = null;
            // check if message contains data payload
            if (remoteMessage.getData() != null) {
                url = remoteMessage.getData().get(PUSH_OPEN_URL);
            }
            final String title = remoteMessage.getNotification().getTitle();
            final String body = remoteMessage.getNotification().getBody();

            onRemoteMessage(url, title, body);
        }

    }


}