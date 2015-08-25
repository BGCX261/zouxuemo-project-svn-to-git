package com.lily.dap.web.ws.demo;

import java.util.List;

import javax.jws.WebService;

import com.lily.dap.entity.demo.Module;

@WebService
public interface ModuleService {
	public List<Module> list();

	public Module get(long id);
	
	public Module save(Module module);
	
	public void remove(long id);
	
	public int batchRemove(long[] ids);
}
