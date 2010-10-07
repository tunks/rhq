package org.rhq.core.pc.upgrade.plugins.v2;

import org.apache.commons.logging.LogFactory;

import org.rhq.core.domain.measurement.AvailabilityType;
import org.rhq.core.pluginapi.inventory.ResourceComponent;
import org.rhq.core.pluginapi.inventory.ResourceContext;

public class ResComponent implements ResourceComponent<ResourceComponent> {
    public AvailabilityType getAvailability() {
        return AvailabilityType.UP;
    }

    public void start(ResourceContext<ResourceComponent> context) {
        LogFactory.getLog(this.getClass()).info(
            "~~~ Starting resource upgrade test plugin v2 component: key=" + context.getResourceKey());
    }

    public void stop() {
    }
}
