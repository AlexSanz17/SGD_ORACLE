package org.ositran.utils;

import java.lang.reflect.Method;

public class AuditoriaUtil {

   public static String obtenerMetodoGet(String propertyName) {
      if (StringUtil.isEmpty(propertyName) || propertyName.length() <= 0) {
         return null;
      }

      char c = Character.toUpperCase(propertyName.charAt(0));

      return "get" + c + propertyName.substring(1);
   }

   public static Object obtenerValor(Object obj, String property) {
      try {
         Method method = obj.getClass().getMethod(obtenerMetodoGet(property));

         return method.invoke(obj);
      } catch (Exception e) {
         throw new RuntimeException("No se puede obtener el valor para " + property, e);
      }
   }
}
