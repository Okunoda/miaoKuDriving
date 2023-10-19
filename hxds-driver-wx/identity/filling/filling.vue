<template>
    <view class="page">
        <view class="notice">
            <u-icon name="info-circle-fill" color="#AC9146" size="30" top="3" />
            <text>准确填写个人信息，可享受每份代驾订单人身意外险</text>
        </view>
        <view class="credentials-container">
            <view class="credentials">
                <image :src="cardBackground[0]" class="bg"></image>
                <view class="cover">
                    <image src="../static/filling/card.png" mode="widthFix" class="card"></image>
                    <text class="desc">身份证正面</text>
                    <ocr-navigator @onSuccess="scanIdcardFront" certificateType="idCard" :opposite="false">
                        <button class="camera"></button>
                    </ocr-navigator>
                </view>
            </view>
            <view class="credentials">
                <image :src="cardBackground[1]" class="bg"></image>
                <view class="cover">
                    <image src="../static/filling/card.png" mode="widthFix" class="card"></image>
                    <text class="desc">身份证背面</text>
                    <ocr-navigator @onSuccess="scanIdcardBack" certificateType="idCard" :opposite="true">
                        <button class="camera"></button>
                    </ocr-navigator>
                </view>
            </view>
            <view class="credentials">
                <image :src="cardBackground[2]" class="bg"></image>
                <view class="cover">
                    <image src="../static/filling/card.png" mode="widthFix" class="card"></image>
                    <text class="desc">手持身份证</text>
                    <button class="camera" @tap="takePhoto('idcardHolding')"></button>
                </view>
            </view>
            <view class="credentials">
                <image :src="cardBackground[3]" class="bg"></image>
                <view class="cover">
                    <image src="../static/filling/card.png" mode="widthFix" class="card"></image>
                    <text class="desc">驾驶证正面</text>
                    <ocr-navigator @onSuccess="scanDrcardFront" certificateType="driverslicense">
                        <button class="camera"></button>
                    </ocr-navigator>
                </view>
            </view>
            <view class="credentials">
                <image :src="cardBackground[4]" class="bg"></image>
                <view class="cover">
                    <image src="../static/filling/card.png" mode="widthFix" class="card"></image>
                    <text class="desc">驾驶证背面</text>
                    <button class="camera" @tap="takePhoto('drcardBack')">
                    </button>
                </view>
            </view>
            <view class="credentials">
                <image :src="cardBackground[5]" class="bg"></image>
                <view class="cover">
                    <image src="../static/filling/card.png" mode="widthFix" class="card"></image>
                    <text class="desc">手持驾驶证</text>
                    <button class="camera" @tap="takePhoto('drcardHolding')"></button>
                </view>
            </view>
        </view>

        <view class="title">个人信息</view>
        <view class="list">
            <u-cell-group border="false">
                <u-cell-item title="真实姓名" :value="idcard.name" :value-style="style" :arrow="false" />
                <u-cell-item title="性别" :value="idcard.sex" :value-style="style" :arrow="false" />
                <u-cell-item title="生日" :value="idcard.birthday" :value-style="style" :arrow="false" />
                <u-cell-item title="身份证号" :value="idcard.pid" :value-style="style" :arrow="false" />
                <u-cell-item title="身份证地址" :value="idcard.shortAddress" :value-style="style"
                    @click="showAddressContent" />
                <u-cell-item title="身份证有效期" :value="idcard.expiration" :value-style="style" :arrow="false" />
            </u-cell-group>
        </view>
        <view class="title">联系方式</view>
        <view class="list">
            <u-cell-group border="false">
                <u-cell-item title="手机号码" :value="contact.tel" :value-style="style"
                    @click="enterContent('手机号码', 'tel')" />
                <u-cell-item title="电子信箱" :value="contact.shortEmail" :value-style="style"
                    @click="enterContent('电子信箱', 'email')" />
                <u-cell-item title="收信地址" :value="contact.shortMailAddress" :value-style="style"
                    @click="enterContent('收信地址', 'mailAddress')" />
                <u-cell-item title="紧急联系人" :value="contact.contactName" :value-style="style"
                    @click="enterContent('紧急联系人', 'contactName')" />
                <u-cell-item title="紧急联系人电话" :value="contact.contactTel" :value-style="style"
                    @click="enterContent('紧急联系人电话', 'contactTel')" />
            </u-cell-group>
        </view>
        <view class="title">驾驶证</view>
        <view class="list">
            <u-cell-group border="false">
                <u-cell-item title="驾驶证类型" :value="drcard.carClass" :value-style="style" :arrow="false" />
                <u-cell-item title="驾驶证有效期" :value="drcard.validTo" :value-style="style" :arrow="false" />
                <u-cell-item title="初次领证日期" :value="drcard.issueDate" :value-style="style" :arrow="false" />
            </u-cell-group>
        </view>

        <button class="btn" @tap="save" :disabled="realAuth == 3">保存信息</button>
        <view class="remark">您每次提交实名信息之后，都需要工作人员严格审查，请等候1~3天，这期间您将无法接单，特此声明！</view>
        <u-toast ref="uToast" />
    </view>
</template>

<script>
    let dayjs = require('dayjs');
    export default {
        data() {
            return {
                mode: 'fill',
                style: {
                    color: '#FF9900'
                },
                cardBackground: [
                    '../static/filling/credentials-bg.jpg',
                    '../static/filling/credentials-bg.jpg',
                    '../static/filling/credentials-bg.jpg',
                    '../static/filling/credentials-bg.jpg',
                    '../static/filling/credentials-bg.jpg',
                    '../static/filling/credentials-bg.jpg'
                ],
                // 身份证信息
                idcard: {
                    pid: '',
                    name: '',
                    sex: '',
                    address: '', //真正提交到数据库中的地址
                    shortAddress: '', //缩略地址
                    birthday: '',
                    expiration: '',
                    idcardFront: '',
                    idcardBack: '',
                    idcardHolding: ''
                },
                // 联系人的信息
                contact: {
                    tel: '',
                    email: '',
                    shortEmail: '',
                    mailAddress: '',
                    shortMailAddress: '',
                    contactName: '',
                    contactTel: ''
                },
                // 驾驶证信息
                drcard: {
                    issueDate: '',
                    carClass: '',
                    validFrom: '',
                    validTo: '',
                    drcardFront: '',
                    drcardBack: '',
                    drcardHolding: ''
                },
                //这两个变量的作用是做对比然后删除云端多余的证件照片的作用
                /**
                 * 由于可能存在的用户对第一次拍照不满意，重新拍照的情况，但是这些照片都已经上传到了云端
                 * 所以将cosImg的地址和currentImg中的照片地址做对比，看哪些照片是多余的，也就是currentImg中不存在，就将其删除。
                 * currentImg就是存放最终要用的照片地址
                 */
                cosImg: [],
                currentImg: {},
                realAuth: uni.getStorageSync('realAuth')
            };
        },

        methods: {
            scanIdcardFront: function(resp) {
                let that = this;
                let detail = resp.detail;
                that.idcard.pid = detail.id.text;
                that.idcard.name = detail.name.text;
                that.idcard.sex = detail.gender.text;
                that.idcard.address = detail.address.text;
                //需要缩略身份证地址，文字太长页面显示不了
                that.idcard.shortAddress = detail.address.text.substr(0, 15) + '...';
                that.idcard.birthday = detail.birth.text;
                //OCR插件拍摄到的身份证正面照片存储地址
                that.idcard.idcardFront = detail.image_path;
                //让身份证View标签加载身份证正面照片
                that.cardBackground[0] = detail.image_path;
                //上传身份证正面照片
                that.uploadCos(that.url.uploadCosPrivateFile, detail.image_path, 'driverAuth', function(resp) {
                    //此时的data是字符串，将其转为json格式
                    let data = JSON.parse(resp.data);
                    //身份证照片的云端URL地址
                    let path = data.path;
                    //页面持久层保存身份证云端URL地址
                    that.currentImg['idcardFront'] = path;

                    /*
                     * 本页面所有上传到云端的照片云端URL地址都保存到数组中，因为用户可以反复拍摄身份证
                     * 照片，那么之前上传的照片到最后应该从云端删除掉。页面提交完整实名认证信息的时候，
                     * 需要比对cosImg数组中哪些照片不需要了，让云端删除不需要的证件照片
                     */
                    that.cosImg.push(path);
                });
            },
            scanIdcardBack: function(resp) {
                let that = this;
                let detail = resp.detail;
                //OCR插件拍摄到的身份证背面照片存储地址
                that.idcard.idcardBack = detail.image_path;
                //让身份证View标签加载身份证背面照片
                that.cardBackground[1] = detail.image_path;
                //日期类似这种格式 “19001213-20210121” ，遂拆分取后者为过期时间
                let validDate = detail.valid_date.text.split("-")[1];
                that.idcard.expiration = dayjs(validDate, "YYYYMMDD").format("YYYY-MM-DD");

                that.uploadCos(that.url.uploadCosPrivateFile, detail.image_path, "driverAuth", function(resp) {
                    let data = JSON.parse(resp.data);
                    //身份证照片的云端URL地址
                    let path = data.path;
                    //页面持久层保存身份证云端URL地址
                    that.currentImg['idcardBack'] = path;
                    that.cosImg.push(path);
                })

            },

            updatePhoto: function(type, path) {
                let that = this;
                console.log("into updatePhoto method")
                that.uploadCos(that.url.uploadCosPrivateFile, path, "driverAuth", function(resp) {
                    console.log("into uploadCos method")
                    let data = JSON.parse(resp.data);
                    let path = data.path;
                    that.cosImg.push(path);

                    //判断回传的照片的类型是什么
                    if (type == 'idcardHolding') {
                        console.log("into if method")
                        //手持身份证
                        that.cardBackground[2] = path;
                        that.currentImg['idcardHolding'] = data.path;
                        that.idcard.idcardHolding = data.path;
                    } else if (type == 'drcardBack') {
                        that.cardBackground[4] = path;
                        that.currentImg['drcardBack'] = data.path;
                        that.drcard.drcardBack = data.path;
                    } else if (type == 'drcardHolding') {
                        that.cardBackground[5] = path;
                        that.currentImg['drcardHolding'] = data.path;
                        that.drcard.drcardHolding = data.path;
                    }
                });
                console.log("uploadCos method over")
                //强制刷新小程序视图层
                that.$forceUpdate()
            },
            
            takePhoto: function(type) {
                uni.navigateTo({
                    url: "../identity_camera/identity_camera?" + type
                })
            },

            scanDrcardFront: function(resp) {
                let that = this;
                let detail = resp.detail;
                that.drcard.issueDate = detail.issue_date.text; //初次领证日期
                that.drcard.carClass = detail.car_class.text; //准驾车型
                that.drcard.validFrom = detail.valid_from.text; //驾驶证起始有效期
                that.drcard.validTo = detail.valid_to.text; //驾驶证截止有效期
                that.drcard.drcardFront = detail.image_path;
                that.cardBackground[3] = detail.image_path;
                //把驾驶证正面照片上传到云端
                that.uploadCos(that.url.uploadCosPrivateFile, detail.image_path, 'driverAuth', function(resp) {
                    let data = JSON.parse(resp.data);
                    let path = data.path;
                    that.currentImg['drcardFront'] = path;
                    that.cosImg.push(path);
                });
            },


        },

        onLoad: function(options) {
            let that = this;
            that.mode = options.mode;
        }
    }
</script>

<style lang="less">
    @import url('filling.less');
</style>