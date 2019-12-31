package medicalpatient.utils;

import java.io.Serializable;

/**
 * Created by jorge_moreno on 29/01/18.
 */

public class ListDuo implements Serializable{

    // --------------- Contants -------------///

    /**
     * Default size of array
     */
    public int SIZE_DEFAULT = 8;

    // --------------- Atributes -------------///

    /**
     * size of list
     */
    private int size;

    /**
     * Array of elements of list
     */
    private Duo[] array;

    // ---------------  Contructors  -------------- //

    /**
     *
     */
    public ListDuo(){
        size = 0;
        array = new Duo[SIZE_DEFAULT];
    }

    /**
     *
     * @param size
     */
    public ListDuo(int size){
        this.size = 0;
        array = new Duo[SIZE_DEFAULT];
    }

    // ---------------- Methods ----------------- //

    /**
     *
     * @return
     */
    public int size(){
        return size;
    }

    /**
     *
     * @return
     */
    public Duo[] getArray(){
        return  array;
    }

    /**
     *
     * @param i
     * @return
     */
    public Duo get(int i){
        if( i >- 1 &&  i < size)
            return array[i];
        else
            return  null;
    }

    /**
     *
     * @param value1
     * @param value2
     * @return
     */
    public boolean add(String value1, String value2){

        if(size == array.length)
            resize();

        Duo duo = new Duo(value1, value2);
        array[size] = duo;
        size++;

        return  true;
    }

    /**
     *
     * @return
     */
    public boolean remove(){
        if(size>0){
            array[size-1] = null;
            size--;
            return true;
        }else
            return false;
    }

    /**
     *
     * @param pos
     * @return
     */
    public boolean remove(int pos){
        if(pos>-1 && pos<size){
            array[pos] = null;
            size--;
            return true;
        }else
            return false;
    }
    /**
     *
     */
    private void resize(){
        Duo[] tmp = array;
        array = new Duo[size*2-1];
        for (int i = 0; i < tmp.length; i++)
            array[i] = tmp[i];
    }

    /**
     *
     * @param duo
     * @return
     */
    public boolean add(Duo duo) {
        if (size == array.length)
            resize();

        array[size] = duo;
        size++;

        return true;
    }

    public void set(int pos, String value1 , String value2){
        if(pos>-1 && pos<size){
            array[pos] = new Duo(value1,value2);
        }
    }

    /**
     *
     */
    public class Duo implements Serializable{

        /**
         *  first Element
         */
        private String value1;

        /**
         *  second Element
         */
        private String value2;

        /**
         *
         * @param value1
         * @param value2
         */
        public Duo(String value1, String value2){
            this.value1 = value1;
            this.value2 = value2;
        }

        /**
         *
         * @return
         */
        public String getValue1() {
            return value1;
        }

        /**
         *
         * @return
         */
        public String getValue2() {
            return value2;
        }

        /**
         *
         * @param value1
         */
        public void setValue1(String value1) {
            this.value1 = value1;
        }

        /**
         *
         * @param value2
         */
        public void setValue2(String value2) {
            this.value2 = value2;
        }

    }

}