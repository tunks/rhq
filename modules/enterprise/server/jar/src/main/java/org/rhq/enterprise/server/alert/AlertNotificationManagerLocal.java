/*
 * RHQ Management Platform
 * Copyright (C) 2005-2008 Red Hat, Inc.
 * All rights reserved.
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation version 2 of the License.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 675 Mass Ave, Cambridge, MA 02139, USA.
 */
package org.rhq.enterprise.server.alert;

import java.util.List;
import java.util.Map;

import javax.ejb.Local;

import org.rhq.core.domain.alert.notification.AlertNotification;
import org.rhq.core.domain.auth.Subject;
import org.rhq.core.domain.configuration.definition.ConfigurationDefinition;
import org.rhq.enterprise.server.plugin.pc.alert.AlertSender;
import org.rhq.enterprise.server.plugin.pc.alert.AlertSenderInfo;
import org.rhq.enterprise.server.plugin.pc.alert.CustomAlertSenderBackingBean;

/**
 * @author Joseph Marques
 * @author Lukas Krejci
 */
@Local
public interface AlertNotificationManagerLocal {

    /**
     * Add a new AlertNotification to the passed definition
     * @param user subject of the caller
     * @param alertDefinitionId Id of the alert definition
     * @param senderName shortName of the {@link AlertSender}
     * @param configuration Properties for this alert sender.
     * @throws AlertNotificationValidationException if the alert sender of the notification failed to finalize and validate the provided notification's configuration
     * @deprecated this is used only by the legacy JSF UI and no longer works correctly
     */
    @Deprecated
    AlertNotification addAlertNotification(Subject user, int alertDefinitionId, AlertNotification notification) throws AlertDefinitionUpdateException, AlertNotificationValidationException;

    /**
     * Persist changes to the passed {@link AlertNotification}
     *
     * @param notification
     * @throws AlertNotificationValidationException if the alert sender of the notification failed to finalize and validate the notification's configuration
     * @deprecated this is used only by the legacy JSF UI and no longer works correctly
     */
    @Deprecated
    void updateAlertNotification(Subject subject, int alertDefinitionId, AlertNotification notification) throws AlertDefinitionUpdateException, AlertNotificationValidationException;

    /**
     * Remove the passed notifications from the passed alert definition (all identified by their id)
     * @param subject Caller
     * @param alertDefinitionId alert definition to modify
     * @param notificationIds Notifications to remove
     * @return number of notifications removed
     * @deprecated this is used only by the legacy JSF UI and no longer works correctly
     */
    @Deprecated
    int removeNotifications(Subject subject, Integer alertDefinitionId, Integer[] notificationIds);

    /**
     * This method calls out to the alert senders responsible to individual notifications and ask
     * them to finalize and validate the notifications before they are processed further.
     * This gives the alert senders the chance to transform their configurations from what's being
     * input by the user to what needs to be persisted and to perform validation of the configurations.
     * <p>
     * The notifications can be modified during this call. New properties can be added to their configurations, etc.
     * 
     * @param subject the subject that is requesting the changes
     * @param notifications the notifications to process
     * @return true if everything went ok, false if the validation fails. In this case one or more properties
     * in the configuration or extra configuration of one or more of the notifications contains an error message
     * describing the error.
     */
    boolean finalizeNotifications(Subject subject, List<AlertNotification> notifications);
    
    int purgeOrphanedAlertNotifications();

    /**
     * Return a list of all available AlertSenders in the system by their shortname.
     * @return list of senders.
     */
    List<String> listAllAlertSenders();

    ConfigurationDefinition getConfigurationDefinitionForSender(String shortName);

    /**
     * Return notifications for a certain alertDefinitionId
     *
     * NOTE: this only returns notifications that have an AlertSender defined.
     *
     * @param user Subject of the caller
     * @param alertDefinitionId Id of the alert definition
     * @return list of defined notification of the passed alert definition
     *
     *
     */
    List<AlertNotification> getNotificationsForAlertDefinition(Subject user, int alertDefinitionId);

    AlertSenderInfo getAlertInfoForSender(String shortName);

    /**
     * Return the backing bean for the AlertSender with the passed shortNama
     * @param shortName name of a sender
     * @param alertNotificationId
     * @return an initialized BackingBean or null in case of error
     */
    CustomAlertSenderBackingBean getBackingBeanForSender(String shortName, Integer alertNotificationId);

    String getBackingBeanNameForSender(String shortName);

    AlertNotification getAlertNotification(Subject user, int alertNotificationId);

    int cleanseAlertNotificationBySubject(int subjectId);

    int cleanseAlertNotificationByRole(int roleId);
    
    /**
     * This method can be used to reconfigure a number of notifications en-masse.
     * 
     * @param alertNotificationIds the alert notifications to update
     * @param newConfigurationValues a map where keys are the property names and values are the new property values
     */
    void massReconfigure(List<Integer> alertNotificationIds, Map<String, String> newConfigurationValues);
}
