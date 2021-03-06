/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package de.extra.client.logging;

import java.io.File;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.Assert;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.joran.JoranConfigurator;
import ch.qos.logback.core.joran.spi.JoranException;
import de.extra.client.core.ReturnCode;

/**
 * @author Thorsten Vogel
 * @version $Id: LogFileHandler.java,v 1.2 2012/01/23 14:51:00 zf4iks1 Exp $
 */
public class LogFileHandler {

    private static final String DEFAULT_LOGBACK_CONFIG = "/default-logging-config.xml";

    private static final String LOGBACK_CONFIG_FILE = "logging-config.xml";

    private static final Logger LOG = LoggerFactory.getLogger(LogFileHandler.class);

    private final File logDirectory;

    private final File configurationDirectory;

    /**
     * @param logDirectory
     * @param configurationDirectory
     */
    public LogFileHandler(final File logDirectory, final File configurationDirectory) {
        Assert.notNull(logDirectory, "logDirectory darf nicht leer sein.");
        Assert.notNull(configurationDirectory, "configurationDirectory darf nicht leer sein.");
        this.logDirectory = logDirectory;
        this.configurationDirectory = configurationDirectory;
        initializeDefaultLogging();
        initializeLogFile();
    }

    private void initializeDefaultLogging() {
        final LoggerContext context = (LoggerContext) LoggerFactory.getILoggerFactory();
        final JoranConfigurator configurator = getLoggingConfigurator(context);
        try {
            configurator.doConfigure(new ClassPathResource(DEFAULT_LOGBACK_CONFIG).getInputStream());
        } catch (final Exception e) {
            exitWithError(e.getMessage(), e);
        }
    }

    private void initializeLogFile() {
        final LoggerContext context = (LoggerContext) LoggerFactory.getILoggerFactory();
        final JoranConfigurator configurator = getLoggingConfigurator(context);
        context.putProperty("logfilepath", logDirectory.getAbsolutePath());
        // override default configuration
        try {
            configurator.doConfigure(new File(configurationDirectory, LOGBACK_CONFIG_FILE));
        } catch (final JoranException e) {
            exitWithError(e.getMessage(), e);
        }
        LOG.debug("successfully configured log system, file={}", logDirectory.getAbsolutePath());
    }

    private JoranConfigurator getLoggingConfigurator(final LoggerContext context) {
        final JoranConfigurator configurator = new JoranConfigurator();
        configurator.setContext(context);
        context.reset();
        return configurator;
    }

    private void exitWithError(final String message, final Throwable exception) {
        System.err.println(String.format("Fehler bei der Konfiguration des Loggingsystems: %s", message));
        System.exit(ReturnCode.TECHNICAL.getCode());
    }

}
