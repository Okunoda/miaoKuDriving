<template>
	<div v-if="isAuth(['ROOT', 'VOUCHER:SELECT'])">
		<el-form :inline="true" :model="dataForm" :rules="dataRule" ref="dataForm">
			<el-form-item prop="name">
				<el-input v-model="dataForm.name" placeholder="代金券名称" size="medium" clearable="clearable" />
			</el-form-item>
			<el-form-item prop="tag">
				<el-input v-model="dataForm.tag" placeholder="代金券标签" size="medium" clearable="clearable" />
			</el-form-item>
			<el-form-item>
				<el-select
					v-model="dataForm.totalQuota"
					class="input"
					placeholder="代金券数量"
					size="medium"
					clearable="clearable"
				>
					<el-option label="无限量" value="无限量" />
					<el-option label="有限量" value="有限量" />
				</el-select>
			</el-form-item>
			<el-form-item>
				<el-select
					v-model="dataForm.type"
					class="input"
					placeholder="赠送类型"
					size="medium"
					clearable="clearable"
				>
					<el-option label="用户领取" value="1" />
					<el-option label="注册赠券" value="2" />
					<el-option label="补偿券" value="3" />
				</el-select>
			</el-form-item>
			<el-form-item>
				<el-select
					v-model="dataForm.limitQuota"
					class="input"
					placeholder="用户领券限制"
					size="medium"
					clearable="clearable"
				>
					<el-option label="无限制" value="无限制" />
					<el-option label="有限制" value="有限制" />
				</el-select>
			</el-form-item>
			<el-form-item>
				<el-select
					v-model="dataForm.status"
					class="input"
					placeholder="代金券状态"
					size="medium"
					clearable="clearable"
				>
					<el-option label="正常" value="1" />
					<el-option label="过期" value="2" />
					<el-option label="下架" value="3" />
				</el-select>
			</el-form-item>
			<el-form-item>
				<el-select
					v-model="dataForm.timeType"
					class="input"
					placeholder="有效期限"
					size="medium"
					clearable="clearable"
				>
					<el-option label="无期限" value="无期限" />
					<el-option label="有效天数" value="有效天数" />
					<el-option label="有效日期" value="有效日期" />
				</el-select>
			</el-form-item>

			<el-form-item>
				<el-button size="medium" type="primary" @click="searchHandle()">查询</el-button>
				<el-button size="medium" type="primary" @click="addHandle()">添加</el-button>
				<el-button size="medium" type="danger" @click="deleteHandle()">批量删除</el-button>
			</el-form-item>
		</el-form>
		<el-table
			:data="dataList"
			border
			v-loading="dataListLoading"
			cell-style="padding: 4px 0"
			style="width: 100%;"
			size="medium"
			@selection-change="selectionChangeHandle"
			@expand-change="expand"
			:row-key="getRowKeys"
			:expand-row-keys="expands"
		>
			<el-table-column prop="content" header-align="center" align="center" type="expand">
				<template #default="scope">
					<div class="voucher-table">
						<div class="row">
							<div class="th">总数量</div>
							<div class="th">已领取</div>
							<div class="th">已使用</div>
							<div class="th">最低消费</div>
							<div class="th">领取限制</div>
							<div class="th date">有效期限</div>
						</div>
						<div class="row">
							<div class="td">{{ scope.row.totalQuota != 0 ? scope.row.totalQuota : '无限' }}</div>
							<div class="td">{{ scope.row.takeCount }}</div>
							<div class="td">{{ scope.row.usedCount }}</div>
							<div class="td">{{ scope.row.withAmount }}元</div>
							<div class="td">{{ scope.row.limitQuota }}</div>
							<div class="td" v-if="scope.row.timeType == 0 || scope.row.timeType == null">无</div>
							<div class="td" v-if="scope.row.timeType == 1">{{ scope.row.days }}天以内</div>
							<div class="td" v-if="scope.row.timeType == 2">
								{{ scope.row.startTime }} ~ {{ scope.row.endTime }}
							</div>
						</div>
					</div>

					<div class="voucher-text-table">
						<div class="row">
							<div class="th">【描述文字】</div>
							<div class="td">{{ scope.row.remark }}</div>
						</div>
						<div class="row">
							<div class="th">【标签内容】</div>
							<div class="td">{{ scope.row.tag }}</div>
						</div>
					</div>
				</template>
			</el-table-column>
			<el-table-column
				type="selection"
				:selectable="selectable"
				header-align="center"
				align="center"
				width="50"
			/>
			<el-table-column type="index" header-align="center" align="center" width="100" label="序号">
				<template #default="scope">
					<span>{{ (pageIndex - 1) * pageSize + scope.$index + 1 }}</span>
				</template>
			</el-table-column>
			<el-table-column prop="uuid" header-align="center" align="center" min-width="240" label="编号" />
			<el-table-column prop="name" header-align="center" align="center" min-width="200" label="代金券名称" />
			<el-table-column prop="discount" header-align="center" align="center" min-width="80" label="面额" />
			<el-table-column prop="totalQuota" header-align="center" align="center" min-width="120" label="总数量" />
			<el-table-column prop="takeCount" header-align="center" align="center" min-width="120" label="已领取" />
			<el-table-column prop="type" header-align="center" align="center" min-width="100" label="类型" />
			<el-table-column prop="status" header-align="center" align="center" min-width="100" label="状态" />
			<el-table-column header-align="center" align="center" width="180" label="操作">
				<template #default="scope">
					<el-button
						type="text"
						size="medium"
						@click="updateHandle(scope.row.id, 1, scope.row.id)"
						:disabled="scope.row.status == '正常'"
					>
						上线
					</el-button>
					<el-button
						type="text"
						size="medium"
						@click="updateHandle(scope.row.id, 3, scope.row.id)"
						:disabled="scope.row.status == '下架'"
					>
						下架
					</el-button>
					<el-button
						type="text"
						size="medium"
						:disabled="!isAuth(['ROOT', 'VOUCHER:DELETE']) || scope.row.takeCount > 0"
						@click="deleteHandle(scope.row.id)"
					>
						删除
					</el-button>
				</template>
			</el-table-column>
		</el-table>
		<el-pagination
			@size-change="sizeChangeHandle"
			@current-change="currentChangeHandle"
			:current-page="pageIndex"
			:page-sizes="[10, 20, 50]"
			:page-size="pageSize"
			:total="totalCount"
			layout="total, sizes, prev, pager, next, jumper"
		></el-pagination>
		<add-or-update ref="addOrUpdate" @refreshDataList="loadDataList"></add-or-update>
		<el-dialog v-model="updateVisible" title="提示信息" width="300px" center>
			<div class="notice">你确定{{ operate }}这个代金券？</div>
			<template #footer>
				<span class="dialog-footer">
					<el-button size="medium" @click="updateVisible = false">取消</el-button>
					<el-button type="primary" size="medium" @click="confirmHandle()">确定</el-button>
				</span>
			</template>
		</el-dialog>
	</div>
</template>
<script>
import $ from 'jquery';
import AddOrUpdate from './voucher-add-or-update.vue';
export default {
	components: {
		AddOrUpdate
	},
	data() {
		return {
			dataForm: {
				name: null,
				tag: null,
				totalQuota: null,
				type: null,
				limitQuota: null,
				status: null,
				timeType: null
			},

			dataList: [],
			pageIndex: 1,
			pageSize: 10,
			totalCount: 0,
			dataListLoading: false,
			dataRule: {},
			dataListSelections: [],
			expands: [],
			content: {},
			id: null,
			status: null,
			uuid: null,
			updateVisible: false,
			operate: null,
			getRowKeys(row) {
				return row.id;
			}
		};
	},
	methods: {
		
	},
	created: function() {
		
	}
};
</script>
<style lang="less" scoped="scoped">
@import url('voucher.less');
</style>
