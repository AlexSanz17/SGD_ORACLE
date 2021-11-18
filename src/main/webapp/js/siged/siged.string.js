Siged.String = {};

Siged.String.isEmpty = function(object) {
   if (!object || typeof(object) == "undefined" || object.length <= 0) {
      return true;
   }

   return false;
};
