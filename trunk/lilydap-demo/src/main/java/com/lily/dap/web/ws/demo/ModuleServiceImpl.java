package com.lily.dap.web.ws.demo;

import java.util.List;

import javax.jws.WebService;

import org.springframework.beans.factory.annotation.Autowired;

import com.lily.dap.entity.demo.Module;
import com.lily.dap.service.demo.ModuleManager;

@WebService(endpointInterface = "com.lily.dap.web.ws.demo.ModuleService", serviceName = "/ModuleService")
public class ModuleServiceImpl implements ModuleService {
	@Autowired
	private ModuleManager moduleManager;
	
	public List<Module> list() {
		return moduleManager.query(null);
	}

	public Module get(long id) {
		return moduleManager.get(id);
	}

	public Module save(Module module) {
		return moduleManager.saveOrUpdate(module);
	}

	public void remove(long id) {
		moduleManager.remove(id);
	}

	public int batchRemove(long[] ids) {
		return moduleManager.batchRemove(ids);
	}
}
