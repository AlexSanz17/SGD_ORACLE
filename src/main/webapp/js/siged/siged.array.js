Siged.Array = {};

Siged.Array.indexOf = function(array, fnc) {
   if (!fnc || typeof(fnc) != "function") {
      return -1;
   }

   if (!array || !array.length || array.length <= 0) {
      return -1;
   }

   for (var i = 0; i < array.length; i++) {
      if (fnc(array[i])) {
         return i;
      }
   }

   return -1;
};
