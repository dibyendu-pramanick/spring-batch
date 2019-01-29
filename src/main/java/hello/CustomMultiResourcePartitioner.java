package hello;

import java.util.HashMap;
import java.util.Map;

import org.springframework.batch.core.partition.support.Partitioner;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.core.io.Resource;
import org.springframework.util.Assert;

public class CustomMultiResourcePartitioner implements Partitioner {
	
	private Resource[] resources = new Resource[0];
	
	public void setResources(Resource[] resources) {
        this.resources = resources;
    }

	@Override
	public Map<String, ExecutionContext> partition(int gridSize) {
		System.out.println("inside partition gridSize--"+gridSize);
		Map<String, ExecutionContext> map = new HashMap<String, ExecutionContext>(gridSize);
        int j = 0;
        /*for (Resource resource : resources) {
            ExecutionContext context = new ExecutionContext();
            Assert.state(resource.exists(), "Resource does not exist: " + resource);
            context.putString("fileName", resource.getFilename());

            map.put("partition" + i, context);
            i++;
        }*/
        for (int i = 0;i<gridSize;i++) {
        	ExecutionContext context = new ExecutionContext();
        	context.putString("fileName", resources[0].getFilename());
        	context.putInt("skipCount", j);
        	context.putInt("totalCount", 10);
        	map.put("partition" + i, context);
        	j = j + 10;
        }
        return map;
	}

}
