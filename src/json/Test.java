/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package json;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONObject;
/**
 *
 * @author Tom
 */
public class Test {
        public static void maine(String data) 
    {
        // oznameni
        System.out.println(":test class TEST");
        // hierarchical data in a flattened list
       // String data = "";

        TreeManager treeManager = new TreeManager();
         List<String> path = new ArrayList<>();
         
         JSONObject object = new JSONObject(data);
            String[] keys = JSONObject.getNames(object);

        for (String key : keys)
        {
           Object value = object.get(key);
           if ( value instanceof JSONArray ) {
                // It's an array
                System.out.println("array");
                JSONArray p;
               p = object.getJSONArray(key);
                String text = cyklus (p);
                //interventionJsonArray = (JSONArray)intervention;
            } 
           if ( value instanceof JSONObject ) {
                // It's an array
                System.out.println("object");
                JSONObject o;
               o = object.getJSONObject(key);
                String text = cyklusO (o);
                //interventionJsonArray = (JSONArray)intervention;
            } 
        /*     Iterator iterator = object.keys();
           while(iterator.hasNext()){
            String keyi = (String)iterator.next();
            JSONObject page = object.getJSONObject(keyi);

        //do stuff with the page...
            } */
            // Determine type of value and do something with it...
           // System.out.println(key + " - " + value );
           //System.out.println(object.length());
        }
         
        for (int i=0; i<data.length(); i++) 
        {
            // build the path to our items in the tree
 
                // add this item to our path
                path.add("test");
               // System.out.println(data[1]);
                // will add it unless an Item with this name already exists at this path
               // treeManager.addData(treeManager, path);
            
        }

       // treeManager.getData(data[0]).putValue("MPG", 38);
       // treeManager.getData(data[1]).putValue("MPG", 28);

       // Gson gson = new Gson();

       // System.out.println(gson.toJson(treeManager));
       
       //System.out.println(treeManager);
    }
        public static String cyklus ( JSONArray data) {
            
         //JSONArray object = new JSONArray(data);

        for (int i=0; i<data.length(); i++) 
        {
           Object value = data.get(i);

            System.out.println(i + " - " + value );
        if ( value instanceof JSONObject ) {
            // It's an array
            System.out.println("object");
            JSONObject o;
            o = data.getJSONObject(i);
            String text = cyklusO (o);
 //interventionJsonArray = (JSONArray)intervention;
            } 
           //System.out.println(object.length());
        }
        return "";
       }

    private static String cyklusO(JSONObject o) {
         String[] keys = JSONObject.getNames(o);
        for (String key : keys)
        {
           Object value = o.get(key);

            System.out.println(key + " - " + value );
            if ( value instanceof JSONArray ) {
                // It's an array
                System.out.println("array");
                JSONArray p;
                p = o.getJSONArray(key);
                String text = cyklus (p);
            } 
           //System.out.println(object.length());
        }
        return "";
         
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        
          
    }

    /**
     * This base class provides the hierarchical property of
     * an object that contains a Map of child objects of the same type.
     * It also has a field - Name
     *
     */
    public static abstract class TreeItem implements Iterable<TreeItem>{

        private final Map<String, TreeItem> children;     
        private String name;

        public TreeItem() {
            children = new HashMap();
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void addChild(String key, TreeItem data) 
        {           
            children.put(key, data);
        }

        public TreeItem getChild(String key) 
        {           
            return children.get(key);
        }

        public boolean hasChild(String key) 
        {           
            return children.containsKey(key);
        }

        @Override
        public Iterator iterator() {          
            return children.values().iterator();
        }           
    }

    /**
     * This is our special case, root node. It is a TreeItem in itself
     * but contains methods for building and retrieving items from our tree
     *
     */
    public static class TreeManager extends TreeItem
    {       
        /**
         * Will add an Item to the tree at the specified path with the value
         * equal to the last item in the path, unless that Item already exists 
         */
        public void addData(List<String> path)
        {
            addData(this, path);
        }

        private void addData(TreeItem parent, List<String> path)
        {
            // if we're at the end of the path - create a node
            String data = path.get(0);
            if(path.size() == 1)
            {
                // unless there is already a node with this name
                if(!parent.hasChild(data))
                {
                    Group group = new Group();
                    group.setName(data);
                    parent.addChild(data, group);
                }
            }
            else
            {
                // pass the tail of this path down to the next level in the hierarchy
                addData(parent.getChild(data), path.subList(1, path.size()));
            }
        }

        public Group getData(String[] path)
        {
            return (Group) getData(this, Arrays.asList(path));
        }

        public Group getData(List<String> path)
        {
            return (Group) getData(this, path);
        }

        private TreeItem getData(TreeItem parent, List<String> path)
        {
            if(parent == null || path.size() == 0)
            {
                throw new IllegalArgumentException("Invalid path specified in getData, remainder: " 
                        + Arrays.toString(path.toArray()));
            }
            String data = path.get(0);
            if(path.size() == 1)
            {
                return parent.getChild(data);
            }
            else
            {
                // pass the tail of this path down to the next level in the hierarchy
                return getData(parent.getChild(data), path.subList(1, path.size()));
            }
        }
    }

    public static class Group extends TreeItem {

        private Map<String, Object> properties;

        public Object getValue(Object key) {
            return properties.get(key);
        }

        public Object putValue(String key, Object value) {
            return properties.put(key, value);
        }

        public Group () {
            super();
            properties = new HashMap<>();
        }       
    }
    
}
