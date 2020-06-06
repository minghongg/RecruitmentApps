<?php
	use Restserver\Libraries\REST_Controller;

	defined('BASEPATH') OR exit('No direct script access allowed');
	
	require APPPATH . 'libraries/REST_Controller.php';
	require APPPATH . 'libraries/Format.php';

	class GetApplicants extends REST_Controller{
		public function __construct()
		{
			parent::__construct();
			$this->load->model('Perusahaan_model','perusahaan');
		}
		public function index_post(){
			$perusahaanID = $this->post('PerusahaanID');
			$job = $this->perusahaan->getApplicants($perusahaanID);
			if($job!=null){
				$this->response([
					'status' => 200,
					'message' => null,
					'data'=> $job
                ], REST_Controller::HTTP_OK ); 
			}
			else{
				$this->response([
					'status' => 200,
					'message' => null,
					'data'=> null
                ], REST_Controller::HTTP_OK ); 
			}
		}
	}
?>
