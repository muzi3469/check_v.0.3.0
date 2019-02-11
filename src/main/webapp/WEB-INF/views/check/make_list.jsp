<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>

	<div class="check-list list-wrapper" style="background-color: #c6e0be;">
		<div class="list check-list-content">
			<span class="check-list-id" style="display: none;">9</span> <span
				class="check-list-index" style="display: none;">0</span>
			<div style="cursor: pointer;"
				class="list-header check-list-header u-clearfix is-menu-shown">
				<div class="list-header-target check-editing-target"></div>
				<a class="list-header-name-assist check-list-name-assist"
					style="font-size: 18px; font-weight: bold; color: black; text-decoration: none;">hello1</a>
				<textarea
					class="list-header-name mod-list-name check-list-name-input form-control"
					style="resize: none; display: none; height: 40px;"></textarea>
			</div>
			<!-- list-header check-list-header u-clearfix is-menu-shown -->

			<!-- ****************** here -> card list start ****************** -->

			<div class="list-card check-member-droppable ui-droppable"
				style="background-color: white; margin-top: 5px; margin-bottom: 5px; padding: 5px; border-radius: 3px; box-shadow: 1px 1px 5px 0 gray; cursor: pointer; padding-left: 8px;">
				<div class="list-card-cover check-card-cover"></div>
				<span
					class="icon-sm icon-edit list-card-operation dark-hove check-open-quick-card-editor check-card-menur"></span>
				<div class="list-card-details check-card-details">
					<div class="list-card-labels check-card-labels"></div>
					<span class="list-card-title check-card-title"> <span
						class="card-short-id hide-card" style="display: none;"></span> <span
						class="list-card-index hide-card" style="display: none;"></span>
						card test
					</span>
					<div class="list-card-members check-list-card-members"></div>
				</div>
				<!-- drop zone -> 파일이 드래그앤드랍으로 위에 올라왔을 때 활성화. -->
				<p class="list-card-dropzone"
					style="height: 32px; line-height: 32px; display: none;">Drop
					files to upload.</p>
			</div>

			<!-- ****************** here -> card list end ****************** -->

			<div style="display: none;"
				class="list-cards u-fancy-scrollbar u-clearfix check-list-cards check-list-sortable ui-sortable">
				<div class="card-composer">
					<div class="list-card check-composer">
						<textarea
							class="list-card-composer-textarea check-card-title form-control"
							id="list-card-composer-textarea"
							placeholder="Enter a title for this card..." rows="5" cols="50"
							style="resize: none; border: 0; outline: none;"></textarea>
					</div>
					<!-- list-card,check-composer -->
					<div class="cc-controls u-clearfix">
						<div class="cc-controls-section">
							<input class="btn btn-primary confirm mod-compact check-add-card"
								type="submit" value="Add Card"> <a href="#"
								class="dark-hover check-card-cancel"></a>
						</div>
						<!-- cc-controls-section -->
					</div>
					<!-- cc-controls,u-clearfix -->
				</div>
				<!-- card-composer -->
			</div>
			<!-- list-cards,u-fancy-scrollbar,u-clearfix,check-list-cards,check-list-sortable,ui-sortable -->
			<div class="check-open-card-composer-container"
				style="width: 100%; height: 30px; padding-top: 5px; cursor: pointer;">
				<a class="open-card-composer check-open-card-composer" href="#">
					<span class="check-add-another-card"> Add a card </span>
				</a>
			</div>
		</div>
		<!-- list check-list-content -->
	</div>
	<!-- check-list list-wrapper -->

</body>
</html>