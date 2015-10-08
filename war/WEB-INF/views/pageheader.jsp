
<div class="row">
	<div class="col3">
		<div style="margin-top:12px;margin-left:10px;">
			<div class="ui-grid-a">
				<div class="ui-block-a" style="margin-top: -9px;width:20%">
					<a href="#leftmenu"
						class="ui-btn ui-shadow ui-corner-all ui-icon-bars ui-btn-icon-notext"
						data-intro="Menu button" data-position="bottom">menu</a>
				</div>
				<div class="ui-block-b" style="width:80%">
					<a id="goHome" href="#"> <img alt=""
						src="<c:url value="/resources/img/logo.png"/>" height="30px"
						style="margin-top:0px">
					</a>
				</div>
			</div>
		</div>
	</div>
	<div class="col6">
		<div style="display:inline-block;width:50%"></div>
	</div>
	<div class="col3">
		<div class="ui-grid-a">
			<div class="ui-block-a" style="margin-top: 15px;">
				<span>Hola <sec:authentication property="principal.username" /></span>
			</div>
			<div class="ui-block-b">
				<div data-role="controlgroup" data-type="horizontal">
					<a id="salir" 
						data-role="button" class="ui-btn-active">Salir</a>
				</div>
			</div>
		</div>
	</div>
</div>
