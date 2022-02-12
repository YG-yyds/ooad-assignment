package dependency_injection;

import java.io.*;
import java.lang.reflect.*;
import java.util.Properties;

public class BeanFactoryImpl implements BeanFactory {
    private Properties injectProperties;
    private Properties valueProperties;

    @Override
    public void loadInjectProperties(File file) {
        injectProperties = readProperties(file);
    }

    @Override
    public void loadValueProperties(File file) {
        valueProperties = readProperties(file);
    }

    @Override
    public <T> T createInstance(Class<T> clazz){
        try{
            //find all clazz
            String imp = clazz.getName();
            if(injectProperties.containsKey(imp)){
                clazz = (Class<T>) Class.forName((String) injectProperties.get(imp));
            }
            //get constructor
            Constructor<T> constructor = null;
            for(Constructor c : clazz.getDeclaredConstructors()){
                if(c.getAnnotation(Inject.class)!=null){
                    constructor = c;
                    break;
                }
            }
            if(constructor == null){
                constructor = clazz.getDeclaredConstructor();
            }
            //parameter
            Parameter[] parameters = constructor.getParameters();
            Object[] objects = new Object[parameters.length];

            for (int i = 0; i < parameters.length; i++) {
                Parameter p = parameters[i];
                if(p.getAnnotation(Value.class)!=null){
                    Value valueAnnotation = p.getAnnotation(Value.class);
                    objects[i] = getObj(p.getType(),valueAnnotation);
                }else{
                    objects[i] = createInstance(p.getType());
                }
            }
            T instance = (T) constructor.newInstance(objects);
            //field
            Field[] fields = instance.getClass().getDeclaredFields();
            for(Field f : fields){
                f.setAccessible(true);
                if(f.getAnnotation(Value.class)!=null){
                    Value valueAnnotation = f.getAnnotation(Value.class);
                    f.set(instance,getObj(f.getType(),valueAnnotation));
                }else if(f.getAnnotation(Inject.class)!=null) {
                    f.set(instance, createInstance(f.getType()));
                }
                f.setAccessible(false);
            }
            //method
            Method[] methods = instance.getClass().getMethods();
            for(Method m : methods){
                if(m.getAnnotation(Inject.class)!=null){
                    m.setAccessible(true);
                    Class<?> aClass = m.getParameterTypes()[0];
                    m.invoke(instance, createInstance(aClass));
                    m.setAccessible(false);
                }
            }
            return instance;
        } catch (IllegalAccessException | InstantiationException | InvocationTargetException | NoSuchMethodException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static Properties readProperties(File file) {
        Properties prop = new Properties();
        try {
            InputStream in = new BufferedInputStream(new FileInputStream(file));
            prop.load(in);
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return prop;
    }

    private <T> T getObj(Class<T> clazz, Value valueAnnotation){
        String[] strings = valueProperties.getProperty(valueAnnotation.value()).split(valueAnnotation.delimiter());
        if (clazz == byte.class) {
            byte obj = (byte)0;
            for (String string : strings) {
                byte val = Byte.parseByte(string);
                if (valueAnnotation.min() <= val && val <= valueAnnotation.max()) {
                    obj = val;
                    break;
                }
            }
            return (T) (Byte) obj;
        }else if (clazz == short.class){
            short obj = (short)0;
            for (String string : strings) {
                short val = Short.parseShort(string);
                if (valueAnnotation.min() <= val && val <= valueAnnotation.max()) {
                    obj = val;
                    break;
                }
            }
            return (T) (Short) obj;
        }else if (clazz == int.class){
            int obj = (int)0;
            for (String string : strings) {
                int val = Integer.parseInt(string);
                if (valueAnnotation.min() <= val && val <= valueAnnotation.max()) {
                    obj = val;
                    break;
                }
            }
            return (T) (Integer) obj;
        }else if (clazz == long.class){
            long obj = (long)0;
            for (String string : strings) {
                long val = Long.parseLong(string);
                if (valueAnnotation.min() <= val && val <= valueAnnotation.max()) {
                    obj = val;
                    break;
                }
            }
            return (T) (Long) obj;
        }else if (clazz == float.class){
            return (T) (Float) Float.parseFloat(strings[0]);
        }else if (clazz == double.class){
            return (T) (Double) Double.parseDouble(strings[0]);
        }else if (clazz == boolean.class){
            return (T) (Boolean) Boolean.parseBoolean(strings[0]);
        }else if (clazz == char.class){
            return (T) (Character) strings[0].charAt(0);
        }else {
            String obj = "default value";
            for (String string : strings) {
                int len = string.length();
                if (valueAnnotation.min() <= len && len <= valueAnnotation.max()) {
                    obj = string;
                    break;
                }
            }
            return (T) obj;
        }
    }
}