# Flutter TODO

## 架构模式

- [ ] todo

## 网络与存储

- [ ] todo

## 异步

### 理解 Flutter 的单线程模型与异常捕获

单线程模型：

- Dart 在单线程中是以消息循环机制来运行的，其中包含两个任务队列，一个是“微任务队列” microtask queue，另一个叫做“事件队列” event queue，微任务队列的执行优先级高于事件队列。
- 在Dart中，所有的外部事件任务都在事件队列中，如IO、计时器、点击、以及绘制事件等，而微任务通常来源于Dart内部，并且微任务非常少。
- 我们可以通过 Future.microtask(…) 方法向微任务队列插入一个任务。
- 由于是单线程模型任务模型，一个任务的失败不会导致另一个任务的实现，所以当某个任务发生异常并没有被捕获时，程序并不会退出，而直接导致的结果是当前任务的后续代码就不会被执行了。

捕获异常：

- 自定义错误处理器：FlutterError.onError
- try-catch 捕获不到异步任务的异常，可以使用 `runZoned(...)`，这类似让任务运行在一个沙箱中，我们可以捕获沙箱中的任何异常信息。

## 常用开源库学习

- <https://pub.dev/packages/provider>
- <https://pub.dev/packages/auto_size_text>
- <https://api.flutter.dev/flutter/dart-core/DateTime-class.html>