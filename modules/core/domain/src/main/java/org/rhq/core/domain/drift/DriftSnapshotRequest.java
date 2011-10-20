/*
 * RHQ Management Platform
 * Copyright (C) 2005-2011 Red Hat, Inc.
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

package org.rhq.core.domain.drift;

import java.io.Serializable;

/**
 * An immutable class used to specify the characteristics of a requested DriftSnapshot. See 
 * {@link org.rhq.enterprise.server.drift.DriftManagerLocal#getSnapshot(org.rhq.core.domain.auth.Subject, DriftSnapshotRequest)}.
 * <pre>
 * Defaults:
 * startVersion = 0 (initial snapshot)
 * includeDriftDirectories = false
 * includeDriftInstances = true;
 * </pre>
 * @author Jay Shaughnessy
 */
public class DriftSnapshotRequest implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer driftDefinitionId;
    private String changeSetId;
    private Integer version;
    private Integer startVersion;
    private String directory;
    private boolean includeDriftDirectories;
    private boolean includeDriftInstances;

    /**
     * This should not be used. It's to satisfy smartgwt.
     */
    public DriftSnapshotRequest() {
    }

    /**
     * Get the current (most recent) snapshot for the specified drift definition.
     * </br>
     * </br> 
     * 
     * @param driftDefinitionId
     */
    public DriftSnapshotRequest(int driftDefinitionId) {
        this(driftDefinitionId, null, null, null, false, true);
    }

    public DriftSnapshotRequest(int driftDefinitionId, Integer version) {
        this(driftDefinitionId, version, null, null, false, true);
    }

    public DriftSnapshotRequest(int driftDefinitionId, Integer version, String directory) {
        this(driftDefinitionId, version, null, directory, false, true);
    }

    public DriftSnapshotRequest(String changesetId) {
        this.changeSetId = changesetId;
        startVersion = 0;
        includeDriftInstances = true;
    }

    /**
     * @param driftDefinitionId
     * @param version null or < 0 or > most recent will default to most recent
     * @param startVersion null or < 0 or > version will default to 0
     * @param directory if specified, limit the snapshot to the specified directory 
     * @param includeDriftDirectories
     * @param includeDriftInstances
     */
    public DriftSnapshotRequest(int driftDefinitionId, Integer version, Integer startVersion, String directory,
        boolean includeDriftDirectories, boolean includeDriftInstances) {
        super();

        this.driftDefinitionId = driftDefinitionId;
        this.version = (null != version && version >= 0) ? version : null;
        this.startVersion = (null != startVersion && startVersion >= 0 && (null == this.version || startVersion > this.version)) ? startVersion
            : 0;
        if (null != directory) {
            directory = directory.trim();
            this.directory = "".equals(directory) ? "./" : directory;
        }
        this.includeDriftDirectories = includeDriftDirectories;
        this.includeDriftInstances = includeDriftInstances;
    }

    public Integer getDriftDefinitionId() {
        return driftDefinitionId;
    }

    public String getChangeSetId() {
        return changeSetId;
    }

    public Integer getVersion() {
        return version;
    }

    public Integer getStartVersion() {
        return startVersion;
    }

    public String getDirectory() {
        return directory;
    }

    public boolean isIncludeDriftDirectories() {
        return includeDriftDirectories;
    }

    public boolean isIncludeDriftInstances() {
        return includeDriftInstances;
    }

    @Override
    public String toString() {
        return "DriftSnapshotRequest [directory=" + directory + ", driftDefinitionId=" + driftDefinitionId
            + ", includeDriftDirectories=" + includeDriftDirectories + ", includeDriftInstances="
            + includeDriftInstances + ", startVersion=" + startVersion + ", version=" + version + "]";
    }

}
