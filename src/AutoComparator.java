import Entity.d;
import Entity.sub;

import java.io.File;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

import java.util.*;

public class AutoComparator {
    String path = "/Users/Rx-zpc/Desktop/git clone/AutoComparator/src/Entity";
    File classes = new File(path);
    static Map<Class<?>,Comparator> comparatorMap = new HashMap<>();
    public void generate() throws Exception{
        if(classes.isDirectory()){
            File[] list = classes.listFiles();
            for(File file:list) {
                String fileName = file.getName();
                String clazzName = fileName.substring(0, fileName.length() - 5);
                Class<?> clazz = Class.forName("Entity."+clazzName);
                if (containAnnotation(clazz)) {

                        Comparator<?> comparator = new Comparator() {
                            @Override
                            public int compare(Object o1, Object o2) {

                                Field[] fields = o1.getClass().getFields();
                                for (Field field : fields) {
                                    Object temp = null;
                                    try {
                                        temp = field.get(o1);
                                        if (isBaseDataType(temp.getClass())) {
                                            int compare = 0;
                                            if ((compare = compareBasic(temp.getClass(), field, o1, o2)) == 0) continue;
                                            else return compare;
                                        } else {
                                            if (containAnnotation(temp.getClass())) {
                                                int compare = 0;
                                                if ((compare = recursion(temp.getClass(), field.get(o1), field.get(o2))) == 0) continue;
                                                else return compare;

                                            }
                                        }
                                    }catch (Exception e){
                                        e.printStackTrace();
                                    }
                                    return 0;
                                }
                                return 0;
                            }
                        };
                    comparatorMap.put(clazz, comparator);
                }


            }
                }
            }




    private static boolean containAnnotation(Class<?> clazz) {
        boolean bl = false;

        Annotation[] annotations = clazz.getAnnotations();
        for (int j = 0; j < annotations.length; j++) {
            String annotationName = annotations[j].annotationType().getName();
            if (annotationName.equals("Entity.DynamicCOMP")) {
                bl = true;
                break;
            }
        }
        return bl;
    }

    private static int recursion (Class<?> clazz, Object o1,Object o2) throws Exception{
        Field[] fields = clazz.getDeclaredFields();
        for(Field field:fields){
            Object temp = field.get(o1);
            if(isBaseDataType(temp.getClass())){
                return compareBasic(clazz,field,o1,o2);
            }else return recursion(temp.getClass(),field.get(o1),field.get(02));
        }
        return 0;
    }

    private static boolean isBaseDataType(Class clazz)
    {
        return
                (
                                clazz.getName().equals(Integer.class.getName())||
                                clazz.equals(Long.class) ||
                                clazz.equals(Double.class) ||
                                clazz.equals(Float.class) ||
                                clazz.equals(Boolean.class) ||
                                clazz.equals(Character.class)||
                                clazz.equals(Short.class)||
                                clazz.equals(Byte.class)||
                                clazz.isPrimitive()
                );
    }



    private static int compareBasic(Class<?> primitiveClass,Field field,Object o1,Object o2) throws Exception{
        if(primitiveClass.equals(Integer.class)){
            return intComp((Integer) field.get(o1),(Integer) field.get(o2));
        }
        if(primitiveClass.equals(Long.class)){
            return longComp((Long) field.get(o1),(Long) field.get(o2));
        }
        if(primitiveClass.equals(Float.class)){
            return floatComp((Float) field.get(o1),(Float) field.get(o2));
        }
        if(primitiveClass.equals(Double.class)){
            return doubleComp((Double) field.get(o1),(Double) field.get(o2));
        }
        return 0;
    }
    private static int intComp(Integer i1, Integer i2) {
        int a = -1, b = -1;
        if (i1 != null) {
            a = i1;
        }
        if (i2 != null) {
            b = i2;
        }
        return a - b;
    }

    private static int longComp(Long l1, Long l2) {
        long a = -1, b = -1;
        if (l1 != null) {
            a = l1;
        }
        if (l2 != null) {
            b = l2;
        }
        return (a == b) ? 0 : (a - b) > 0 ? 1 : -1;
    }

    private static int floatComp(Float f1, Float f2) {
        float a = -1, b = -1;
        if (f1 != null) {
            a = f1;
        }
        if (f2 != null) {
            b = f2;
        }
        return (a == b) ? 0 : (a - b) > 0 ? 1 : -1;
    }

    private static int doubleComp(Double d1, Double d2) {
        double a = -1, b = -1;
        if (d1 != null) {
            a = d1;
        }
        if (d2 != null) {
            b = d2;
        }
        return (a == b) ? 0 : (a - b) > 0 ? 1 : -1;
    }

    private static int booleanComp(Boolean b1, Boolean b2) {
        int a = -1, b = -1;
        if (b1 != null) {
            a = b1 ? 1 : 0;
        }
        if (b2 != null) {
            b = b2 ? 1 : 0;
        }
        return a - b;
    }
    private static int characterComp(Character c1, Character c2) {
        char a ='0', b = '0';
        if (c1 != null) {
            a = c1;
        }
        if (c2 != null) {
            b = c2 ;
        }
        return a - b;
    }

    private static int ShortComp(Short s1, Short s2) {
        Short a =-1, b = -1;
        if (s1 != null) {
            a = s1;
        }
        if (s2 != null) {
            b = s2 ;
        }
        return a - b;
    }

    private static int ByteComp(Byte b1, Byte b2) {
        byte a = 0, b = 0;
        if (b1 != null) {
            a = b1;
        }
        if (b2 != null) {
            b = b2 ;
        }
        return a - b;
    }

    public static void main(String[] args)throws Exception{
        AutoComparator a = new AutoComparator();
        a.generate();
        d a1 = new d();
        a1.a = new sub();
        a1.a.a = 1;
        a1.b = 2;
        d a2 = new d();
        a2.a = new sub();
        a2.a.a = 1;
        a2.b = 1;
        List<d> list = new ArrayList<>();
        list.add(a1);
        list.add(a2);
        Collections.sort(list,comparatorMap.get(d.class));

    }

}



