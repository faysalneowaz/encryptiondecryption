import 'package:flutter/material.dart';
import 'package:flutter/services.dart';

void main() {
  runApp(const MyApp());
}

class MyApp extends StatelessWidget {
  const MyApp({super.key});

  // This widget is the root of your application.
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'Flutter Demo',
      theme: ThemeData(
        primarySwatch: Colors.blue,
      ),
      home: const MyHomePage(title: 'Flutter Demo Home Page'),
    );
  }
}

class MyHomePage extends StatefulWidget {
  const MyHomePage({super.key, required this.title});


  final String title;

  @override
  State<MyHomePage> createState() => _MyHomePageState();
}

class _MyHomePageState extends State<MyHomePage> {
static const channel = const MethodChannel("com.example.encdecjava");
static const channel1 = MethodChannel("dec");
TextEditingController sss = TextEditingController();

String encText = "";
String decText = "";
Future<void> encryptdec(String text, String key) async{

  var s = await channel.invokeMethod("encrypt", <String, String>{
    'text': text,
    'key': key
  });
  print("encdata"+s);
  setState(() {
    encText = s;
  });


}
Future<void> decrypt(String text,String key) async{
  var res = await channel.invokeMethod("decrypt",<String,String>{
    'text': text,
    'key': key
  });
  print("encdata"+res);
  //return res;
  setState(() {
    decText = res;
  });
}
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text(widget.title),
      ),
      body: Center(
        child: Column(
          mainAxisAlignment: MainAxisAlignment.center,
          children: <Widget>[
            TextField(
              controller: sss,
              decoration: const InputDecoration(
                hintText: "Enter Text"
              ),
            ),
            ElevatedButton(onPressed: (){
              encryptdec(sss.text,"123@#!Faysal");

            }, child: Text("Encrypt"),),
            Text(encText),
            ElevatedButton(onPressed: (){
              decrypt(encText,"123@#!Faysal");

            }, child: Text("Decrypt"),),
            Text(decText),
          ],
        ),
      ),

    );
  }
  

}
