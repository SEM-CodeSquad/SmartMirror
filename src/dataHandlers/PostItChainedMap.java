package dataHandlers;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

public class PostItChainedMap<K, V> {

    private static final int MAX = 1024;

    private int size;
    private Map<K, V> postIts;
    private Queue<K> postItsId;

    public PostItChainedMap() {
        this(64);
    }

    public PostItChainedMap(int size) throws IllegalArgumentException{

        if(size<=0){
            throw new IllegalArgumentException("Just Positive Integers Allowed");
        }

        if(size > PostItChainedMap.MAX)
            throw new IllegalArgumentException("Size Cannot be Greater Than: " + PostItChainedMap.MAX);

        this.size = size;
        this.postIts = new HashMap<>(this.size);
        this.postItsId = new LinkedList<>();
    }

    public synchronized void addPostIt(K key, V value){

        if(key == null || value == null)
            throw new NullPointerException("Null Value Not Allowed!");

        if(this.postItsId.contains(key)){
            this.postIts.put(key, value);
        }else{
            this.queuing(key, value);
        }
    }

    public synchronized V getPostIt(K key){

        if(key==null)
            return null;

        return this.postIts.get(key);
    }

    public synchronized void removePostIt(K key){

        if(key == null)
            throw new NullPointerException("Key Cannot be Null!");

        this.postItsId.remove(key);
        this.postIts.remove(key);
    }

    public int size(){
        return this.postItsId.size();
    }

    public void clear(){
        this.postIts.clear();
        this.postItsId.clear();
    }

    public Queue<K> getPostItsId() {
        return postItsId;
    }

    public Map<K, V> getPostIts() {
        return postIts;
    }


    private void queuing(K key, V value){

        if(this.postItsId.size() < this.size){
            if(this.postItsId.add(key)){
                this.postIts.put(key, value);
            }
        }else{
            K old = this.postItsId.poll();
            if(old!=null)
                this.postIts.remove(old);

            this.postItsId.add(key);
            this.postIts.put(key, value);
        }
    }
}