import promptAction from '@ohos.promptAction';
import { askAI } from '../utils/api';

interface ChatMessage {
  role: 'user' | 'assistant';
  content: string;
}

@Entry
@Component
struct AIConsultationPage {
  @State question: string = '';
  @State loading: boolean = false;
  // @State chatHistory: ChatMessage[] = [];
  @State chatHistory: ChatMessage[] = [
    {
      role: 'assistant',
      content: '你好，我是农业智能问答助手，有什么可以帮您？'
    }
  ];

  build() {
    Column() {
      // 头部标题
      Text('🌾 作物智能问答助手')
        .fontSize(26)
        .fontWeight(FontWeight.Bold)
        .padding({ top: 20, bottom: 12 })
        .alignSelf(ItemAlign.Center);

      // 聊天区域
      Scroll() {
        Column({ space: 12 }) {
          ForEach(this.chatHistory, (msg: ChatMessage, index: number) => {
            if (msg.role === 'assistant') {
              // AI 消息靠左
              Row() {
                Text('🤖')
                  .fontSize(20)
                  .margin({ right: 6 });

                Column() {
                  Text(msg.content)
                    .fontSize(16)
                    .fontColor(Color.Black)
                    .backgroundColor('#E0F2F1')
                    .padding(12)
                    .borderRadius(16)
                    .shadow({ radius: 4, color: '#B0BEC5', offsetX: 2, offsetY: 2 })
                    .animation({ duration: 200 });
                }
                .width('83%')  // ✅ 控制 assistant 气泡整体宽度
              }
              .width('100%')
              .justifyContent(FlexAlign.Start)
              .padding({ left: 16, right: 16, bottom: 6 });
            } else {
              // 用户消息靠右
              Row() {
                Text(msg.content)
                  .fontSize(16)
                  .fontColor(Color.Black)
                  .backgroundColor('#BBDEFB')
                  .padding(12)
                  .borderRadius(16)
                  .shadow({ radius: 4, color: '#B0BEC5', offsetX: 2, offsetY: 2 })
                  .animation({ duration: 200 });

                Text('👤')
                  .fontSize(20)
                  .margin({ left: 6 });
              }
              .width('100%')
              .justifyContent(FlexAlign.End)
              .padding({ left: 16, right: 16, bottom: 10 });
            }
          });

        }
        .padding(10)
      }
      .height('60%')
      .width('100%')
      .backgroundColor('#F5F5F5')
      .borderRadius(12)
      .margin({ bottom: 8 });

      // 输入区域
      Column({ space: 12 }) {
        TextInput({
          placeholder: '请输入你的问题…',
          text: this.question
        })
          .type(InputType.Normal)
          .height(90)
          .width('90%')
          .fontSize(16)
          .borderRadius(16)
          .padding(12)
          .backgroundColor('#FFFFFF')
          .onChange((value: string) => this.question = value)
          .shadow({ radius: 3, color: '#B0BEC5', offsetX: 1, offsetY: 2 });

        Button(this.loading ? '⏳ 提交中…' : '📩 提交问题')
          .width('60%')
          .height(48)
          .fontSize(18)
          .borderRadius(24)
          .backgroundColor(this.loading ? '#BDBDBD' : '#4CAF50')
          .fontColor(Color.White)
          .onClick(() => this.sendMessage());
      }
      .alignItems(HorizontalAlign.Center)
    }
    .padding(20)
    .alignItems(HorizontalAlign.Center)
    .justifyContent(FlexAlign.Start)
    .backgroundColor('#ECEFF1');
  }

  private sendMessage() {
    const question = this.question.trim();
    if (!question) {
      promptAction.showToast({ message: '请输入问题' });
      return;
    }

    this.loading = true;
    this.chatHistory.push({ role: 'user', content: question });
    this.question = '';

    // 拼接上下文
    const context = this.chatHistory
      .map(msg => `${msg.role === 'user' ? '用户' : 'AI'}：${msg.content}`)
      .join('\n') + `\n用户：${question}`;

    askAI(context)
      .then(reply => {
        this.chatHistory.push({ role: 'assistant', content: reply });
      })
      .catch((err: Error) => {
        console.error(err.message);
        promptAction.showToast({ message: 'AI 请求失败' });
        this.chatHistory.push({ role: 'assistant', content: '【出错】AI 无法回应，请稍后再试。' });
      })
      .finally(() => {
        this.loading = false;
      });
  }
}
