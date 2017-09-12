/**
 * Zentech-Inc
 * Copyright (C) 2017 All Rights Reserved.
 */
package org.pigeon.router;

import java.util.List;

/**
 * @author lixiang
 * @version $Id Router.java, v 0.1 2017-09-12 9:26 lixiang Exp $$
 */
public interface Router {

    String elect(List<String> servers);

}