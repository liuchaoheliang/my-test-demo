package com.nio.netty;

import java.util.HashMap;
import java.util.Map;

import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

public class Global {
	
	public static ChannelGroup group = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
	public static Map<String, String> users = new HashMap<>();
} 
