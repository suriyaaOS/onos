package org.onlab.onos.impl;

import org.apache.felix.scr.annotations.Activate;
import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Service;
import org.onlab.onos.ApplicationId;
import org.onlab.onos.CoreService;
import org.onlab.onos.Version;
import org.onlab.util.Tools;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Core service implementation.
 */
@Component
@Service
public class CoreManager implements CoreService {

    private static final AtomicInteger ID_DISPENSER = new AtomicInteger(1);

    private static final File VERSION_FILE = new File("../VERSION");
    private static Version version = Version.version("1.0.0-SNAPSHOT");

    private final Map<Short, DefaultApplicationId> appIds = new ConcurrentHashMap<>();

    // TODO: work in progress

    @Activate
    public void activate() {
        List<String> versionLines = Tools.slurp(VERSION_FILE);
        if (versionLines != null && !versionLines.isEmpty()) {
            version = Version.version(versionLines.get(0));
        }
    }

    @Override
    public Version version() {
        return version;
    }

    @Override
    public ApplicationId getAppId(Short id) {
        return appIds.get(id);
    }

    @Override
    public ApplicationId registerApplication(String name) {
        short id = (short) ID_DISPENSER.getAndIncrement();
        DefaultApplicationId appId = new DefaultApplicationId(id, name);
        appIds.put(id, appId);
        return appId;
    }

}
