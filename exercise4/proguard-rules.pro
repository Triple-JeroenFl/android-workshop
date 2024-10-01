# moshi
-keep class com.wearetriple.exercises.ui.model.**.*Response
-dontwarn org.jetbrains.annotations.**
-keep class kotlin.Metadata { *; }

-keepclassmembers class com.wearetriple.exercises.ui.model.**.*Response {
  <init>(...);
  <fields>;
}

# okhttp
-dontwarn okhttp3.**
-dontwarn okio.**
-dontwarn javax.annotation.**
-dontwarn org.conscrypt.**
# A resource is loaded with a relative path so the package of this class must be preserved.
-keepnames class okhttp3.internal.publicsuffix.PublicSuffixDatabase

# retrofit
# Retain generic type information for use by reflection by converters and adapters.
-keepattributes Signature
# Retain service method parameters.
-keepclassmembernames,allowobfuscation interface * {
    @retrofit2.http.* <methods>;
}
# Ignore annotation used for build tooling.
-dontwarn org.codehaus.mojo.animal_sniffer.IgnoreJRERequirement

# kotlinX Serialization
-keepattributes *Annotation*, InnerClasses
-dontnote kotlinx.serialization.SerializationKt
-keep,includedescriptorclasses class com.wearetriple.exercises.**$$serializer { *; }
-keepclassmembers class com.wearetriple.exercises.** {
    *** Companion;
}
-keepclasseswithmembers class com.wearetriple.exercises.** {
    kotlinx.serialization.KSerializer serializer(...);
}