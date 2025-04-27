import { AbilityConstant, ConfigurationConstant, UIAbility, Want } from '@kit.AbilityKit';
import { hilog } from '@kit.PerformanceAnalysisKit';
import { window } from '@kit.ArkUI';

const DOMAIN = 0x0000;

export default class EntryAbility extends UIAbility {
  onCreate(want: Want, launchParam: AbilityConstant.LaunchParam): void {
    this.context.getApplicationContext().setColorMode(ConfigurationConstant.ColorMode.COLOR_MODE_NOT_SET);
    hilog.info(DOMAIN, 'testTag', '%{public}s', 'Ability onCreate');
  }

  onDestroy(): void {
    hilog.info(DOMAIN, 'testTag', '%{public}s', 'Ability onDestroy');
  }




  onWindowStageCreate(windowStage: window.WindowStage): void {

    const isLoggedIn = AppStorage.get<boolean>('isLoggedIn') ?? false
    const targetPage = isLoggedIn ? 'pages/MainPage' : 'pages/LoginPage'


    windowStage.loadContent(targetPage, (err) => {
      if (err.code) {
        hilog.error(DOMAIN, 'testTag', '页面加载失败：%{public}s', JSON.stringify(err))
      }
    })
  }


  // onWindowStageCreate(windowStage: window.WindowStage): void {
  //   windowStage.loadContent('pages/MainPage', (err) => {
  //     if (err.code) {
  //       hilog.error(DOMAIN, 'testTag', '页面加载失败：%{public}s', JSON.stringify(err))
  //     } else {
  //       hilog.info(DOMAIN, 'testTag', 'MainPage 页面加载成功')
  //     }
  //   })
  // }




  onWindowStageDestroy(): void {
    // Main window is destroyed, release UI related resources
    hilog.info(DOMAIN, 'testTag', '%{public}s', 'Ability onWindowStageDestroy');
  }

  onForeground(): void {
    // Ability has brought to foreground
    hilog.info(DOMAIN, 'testTag', '%{public}s', 'Ability onForeground');
  }

  onBackground(): void {
    // Ability has back to background
    hilog.info(DOMAIN, 'testTag', '%{public}s', 'Ability onBackground');
  }
}