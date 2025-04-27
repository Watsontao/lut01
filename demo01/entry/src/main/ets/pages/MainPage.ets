import HomePage from './HomePage'
import CropManagementPage from './CropInfoPage'
import ProfilePage from './ProfilePage'
import CropInfoPage from './CropInfoPage'

@Entry
@Component
export default struct MainPage {
  @State currentIndex: number = 0

  build() {
    Column() {
      // 页面主体区域
      Stack() {
        HomePage().visibility(this.currentIndex === 0 ? Visibility.Visible : Visibility.None)
        CropInfoPage().visibility(this.currentIndex === 1 ? Visibility.Visible : Visibility.None)
        ProfilePage().visibility(this.currentIndex === 2 ? Visibility.Visible : Visibility.None)
      }.layoutWeight(1)

      // 底部 Tab 导航栏
      Row({ space: 30 }) {
        this.buildTabItem('首页', 0)
        // this.buildTabItem('农作物', 1)
        this.buildTabItem('个人中心', 2)
      }
      .padding(16)
      .justifyContent(FlexAlign.SpaceAround)
      .backgroundColor('#eeeeee')
      .width('100%')
    }
  }

  @Builder
  buildTabItem(label: string, index: number): void {
    Column() {
      Text(label)
        .fontSize(16)
        .fontColor(this.currentIndex === index ? '#35a9d0' : '#888')
    }
    .onClick(() => this.currentIndex = index)
    .alignItems(HorizontalAlign.Center)
    .width('30%')
  }
}

